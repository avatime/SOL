package com.finance.backend.group

import com.finance.backend.Exceptions.TokenExpiredException
import com.finance.backend.Exceptions.InvalidUserException
import com.finance.backend.group.request.PublicAccountReq
import com.finance.backend.group.request.RegistPublicAccountReq
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.naming.AuthenticationException

@RestController
@RequestMapping("/api/v1/public")
class GroupController (private val groupService : GroupService) {

    @GetMapping("")
    fun getAllGroups(@RequestHeader("access_token") accessToken : String) : ResponseEntity<Any?>{
        return try{
            ResponseEntity.status(200).body(groupService.getAllGroups(accessToken))
        } catch (e : TokenExpiredException) {
            ResponseEntity.status(403).body("Token Expired")
        } catch (e : InvalidUserException) {
            ResponseEntity.status(409).body("Cannot Found User")
        } catch (e : Exception) {
            ResponseEntity.status(500).body("Internal Server Error")
        }
    }

    @PostMapping("")
    fun createGroup(@RequestHeader("access_token") accessToken : String, @RequestBody registPublicAccountReq: RegistPublicAccountReq) : ResponseEntity<Any?>{
        return try{
            ResponseEntity.status(200).body(groupService.createNewGroup(accessToken, registPublicAccountReq))
        } catch (e : TokenExpiredException) {
            ResponseEntity.status(403).body("Token Expired")
        } catch (e : InvalidUserException) {
            ResponseEntity.status(409).body("Cannot Found User")
        } catch (e : Exception) {
            ResponseEntity.status(500).body("Internal Server Error")
        }
    }

    @PostMapping("/friend")
    fun getAllMembersInGroup(@RequestHeader("access_token") accessToken : String, @RequestBody publicAccountReq: PublicAccountReq) : ResponseEntity<Any?>{
        return try{
            ResponseEntity.status(200).body(groupService.getAllMembersInGroups(accessToken, publicAccountReq.paId))
        } catch (e : TokenExpiredException) {
            ResponseEntity.status(403).body("Token Expired")
        } catch (e : InvalidUserException) {
            ResponseEntity.status(409).body("Cannot Found User")
        } catch (e : NullPointerException) {
            ResponseEntity.status(404).body("Unexpected Server Error")
        } catch (e : AuthenticationException) {
            ResponseEntity.status(403).body("Cannot Show Public Group")
        }  catch (e : Exception) {
            ResponseEntity.status(500).body("Internal Server Error")
        }
    }

    @PutMapping("")
    fun disableGroup(@RequestHeader("access_token") accessToken : String, @RequestBody publicAccountReq: PublicAccountReq) : ResponseEntity<Any?>{
        return try{
            ResponseEntity.status(200).body(groupService.disableExistGroup(accessToken, publicAccountReq.paId))
        } catch (e : TokenExpiredException) {
            ResponseEntity.status(403).body("Token Expired")
        } catch (e : InvalidUserException) {
            ResponseEntity.status(409).body("Cannot Found User")
        } catch (e : NullPointerException) {
            ResponseEntity.status(404).body("Cannot Found Public Group")
        } catch (e : AuthenticationException) {
            ResponseEntity.status(403).body("Cannot Delete Public Group")
        }  catch (e : Exception) {
            ResponseEntity.status(500).body("Internal Server Error")
        }
    }
}