package com.finance.backend.group

import com.finance.backend.Exceptions.TokenExpiredException
import com.finance.backend.Exceptions.InvalidUserException
import com.finance.backend.group.request.*
import org.springframework.http.ResponseEntity
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.web.bind.annotation.*
import javax.naming.AuthenticationException

@RestController
@RequestMapping("/api/v1/public")
class GroupController (private val groupService : GroupService) {

    @GetMapping("")
    fun getAllGroups(@RequestHeader("access_token") accessToken : String) : ResponseEntity<Any?>{
        return ResponseEntity.status(200).body(groupService.getAllGroups(accessToken))
    }

    @PostMapping("")
    fun createGroup(@RequestHeader("access_token") accessToken : String, @RequestBody registPublicAccountReq: RegistPublicAccountReq) : ResponseEntity<Any?>{
        return ResponseEntity.status(200).body(groupService.createNewGroup(accessToken, registPublicAccountReq))
    }

    @PutMapping("")
    fun disableGroup(@RequestHeader("access_token") accessToken : String, @RequestBody publicAccountReq: PublicAccountReq) : ResponseEntity<Any?>{
        return try{
            ResponseEntity.status(200).body(groupService.disableExistGroup(accessToken, publicAccountReq.paId))
        } catch (e : NullPointerException) {
            ResponseEntity.status(404).body("Cannot Found Public Group")
        } catch (e : AuthenticationException) {
            ResponseEntity.status(403).body("Cannot Delete Public Group")
        }
    }

    @PostMapping("/trade")
    fun getAllTradeHistory(@RequestHeader("access_token") accessToken : String, @RequestBody publicAccountReq: PublicAccountReq) : ResponseEntity<Any?>{
        return ResponseEntity.status(200).body(groupService.getAllTradeHistory(accessToken, publicAccountReq.paId))
    }

    @PostMapping("/friend")
    fun getAllMembersInGroup(@RequestHeader("access_token") accessToken : String, @RequestBody publicAccountReq: PublicAccountReq) : ResponseEntity<Any?>{
        return ResponseEntity.status(200).body(groupService.getAllMembersInGroups(accessToken, publicAccountReq.paId))
    }

    @PostMapping("/dues")
    fun getAllDues(@RequestHeader("access_token") accessToken : String, @RequestBody publicAccountReq: PublicAccountReq) : ResponseEntity<Any?>{
        return ResponseEntity.status(200).body(groupService.getAllDues(accessToken, publicAccountReq.paId))
    }

    @PostMapping("/dues/detail")
    fun getDueDetails(@RequestHeader("access_token") accessToken : String, @RequestBody duesReq: DuesReq) : ResponseEntity<Any?>{
        return ResponseEntity.status(200).body(groupService.getDueDetails(accessToken, duesReq.duesId))
    }

    @PostMapping("/dues/pay")
    fun payDue(@RequestHeader("access_token") accessToken : String, @RequestBody duesPayReq: DuesPayReq) : ResponseEntity<Any?>{
        return ResponseEntity.status(200).body(groupService.payDue(accessToken, duesPayReq))
    }

    @PostMapping("/dues/regist")
    fun registDue(@RequestHeader("access_token") accessToken : String, @RequestBody registDueReq: RegistDueReq) : ResponseEntity<Any?>{
        return ResponseEntity.status(200).body(groupService.createDue(accessToken, registDueReq))
    }

    @PutMapping("/dues")
    fun disableGroup(@RequestHeader("access_token") accessToken : String, @RequestBody dueReq: DuesReq) : ResponseEntity<Any?>{
        return ResponseEntity.status(200).body(groupService.disableExistDue(accessToken, dueReq.duesId))
    }

    @PostMapping("/info")
    fun getPublicAccountInfo(@RequestHeader("access_token") accessToken: String, @RequestBody publicAccountReq: PublicAccountReq) : ResponseEntity<Any?>{
        return ResponseEntity.status(200).body(groupService.getPublicAccountInfo(accessToken, publicAccountReq.paId))
    }

    @PostMapping("/withdraw")
    fun publicAccountDuesOut(@RequestHeader("access_token") accessToken: String, publicAccountWithdrawReq: PublicAccountWithdrawReq) : ResponseEntity<Any?> {
        return ResponseEntity.status(200).body(groupService.getMoney(accessToken, publicAccountWithdrawReq))
    }

    @GetMapping("/dues/test/scheduled")
    @Scheduled(cron = "0 0 0 * * *", zone = "Asia/Seoul")
    fun registPaymentMonthly() {
        TODO("정기 회비 생성")
    }
}