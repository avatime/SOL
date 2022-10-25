package com.finance.backend.group

import com.finance.backend.Exceptions.TokenExpiredException
import com.finance.backend.Exceptions.InvalidUserException
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

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
    fun createGroup(@RequestHeader("access_token") accessToken : String) : ResponseEntity<Any?>{
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
}