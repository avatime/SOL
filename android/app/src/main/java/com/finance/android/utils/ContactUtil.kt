package com.finance.android.utils

import android.Manifest
import android.annotation.SuppressLint
import android.content.ContentUris
import android.content.Context
import android.net.Uri
import android.provider.ContactsContract
import androidx.annotation.RequiresPermission
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.finance.android.domain.dto.response.ContactDto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * @author aminography
 */

@RequiresPermission(Manifest.permission.READ_CONTACTS)
fun Context.isContactExists(
    phoneNumber: String
): Boolean {
    val lookupUri = Uri.withAppendedPath(
        ContactsContract.PhoneLookup.CONTENT_FILTER_URI,
        Uri.encode(phoneNumber)
    )
    val projection = arrayOf(
        ContactsContract.PhoneLookup._ID,
        ContactsContract.PhoneLookup.NUMBER,
        ContactsContract.PhoneLookup.DISPLAY_NAME
    )
    contentResolver.query(lookupUri, projection, null, null, null).use {
        return (it?.moveToFirst() == true)
    }
}

@SuppressLint("Range")
@RequiresPermission(Manifest.permission.READ_CONTACTS)
@JvmOverloads
fun Context.retrieveAllContacts(
    searchPattern: String = "",
    retrieveAvatar: Boolean = true,
    page: Int = 1,
    limit: Int = 30
): List<ContactDto> {
    val result: MutableList<ContactDto> = mutableListOf()

    contentResolver.query(
        ContactsContract.Contacts.CONTENT_URI,
        CONTACT_PROJECTION,
        if (searchPattern.isNotBlank()) "${ContactsContract.Contacts.DISPLAY_NAME_PRIMARY} LIKE '%?%'" else null,
        if (searchPattern.isNotBlank()) arrayOf(searchPattern) else null,
        "${ContactsContract.Contacts.DISPLAY_NAME_PRIMARY} ASC LIMIT $limit OFFSET ${(page - 1) * limit - 1}"
    )?.use {
        if (it.moveToFirst()) {
            do {
                val contactId = it.getLong(it.getColumnIndex(CONTACT_PROJECTION[0]))
                val name = it.getString(it.getColumnIndex(CONTACT_PROJECTION[2])) ?: ""
                val phoneNumber = retrievePhoneNumber(contactId) ?: continue
                val avatar = if (retrieveAvatar) retrieveAvatar(contactId) else null
                result.add(ContactDto(contactId, name, phoneNumber, avatar.toString()))
            } while (it.moveToNext())
        }
    }
    return result
}

@SuppressLint("Range")
private fun Context.retrievePhoneNumber(contactId: Long): String? {
    return contentResolver.query(
        ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
        null,
        "${ContactsContract.CommonDataKinds.Phone.CONTACT_ID} =?",
        arrayOf(contactId.toString()),
        null
    )?.use {
        return if (it.moveToFirst()) {
            it.getString(it.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
        } else {
            null
        }
    }
}

private fun Context.retrieveAvatar(contactId: Long): Uri? {
    return contentResolver.query(
        ContactsContract.Data.CONTENT_URI,

        null,
        "${ContactsContract.Data.CONTACT_ID} =? AND ${ContactsContract.Data.MIMETYPE} = '${ContactsContract.CommonDataKinds.Photo.CONTENT_ITEM_TYPE}'",
        arrayOf(contactId.toString()),
        null
    )?.use {
        if (it.moveToFirst()) {
            val contactUri = ContentUris.withAppendedId(
                ContactsContract.Contacts.CONTENT_URI,
                contactId
            )
            Uri.withAppendedPath(
                contactUri,
                ContactsContract.Contacts.Photo.CONTENT_DIRECTORY
            )
        } else null
    }
}

private val CONTACT_PROJECTION = arrayOf(
    ContactsContract.Contacts._ID,
    ContactsContract.Contacts.LOOKUP_KEY,
    ContactsContract.Contacts.DISPLAY_NAME_PRIMARY,
    ContactsContract.Contacts.HAS_PHONE_NUMBER
)

class ContactSource(
    private val context: Context,
    private val searchPattern: String = "",
    private val retrieveAvatar: Boolean = true,
    private val pageSize: Int = 30
) : PagingSource<Int, ContactDto>() {

    @RequiresPermission(value = "android.permission.READ_CONTACTS")
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ContactDto> {
        return try {
            val nextPage = params.key ?: 1
            withContext(Dispatchers.IO) {
                val response = context.retrieveAllContacts(
                    searchPattern = searchPattern,
                    retrieveAvatar = retrieveAvatar,
                    limit = pageSize,
                    page = nextPage
                )

                return@withContext LoadResult.Page(
                    data = response,
                    prevKey = if (nextPage == 1) null else nextPage - 1,
                    nextKey = nextPage + 1
                )
            }
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, ContactDto>): Int? {
        return state.anchorPosition
    }
}
