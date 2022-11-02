from typing import Optional
from pydantic import BaseModel


class userReq(BaseModel):
    user_id: Optional[bytes]


class Account(BaseModel):
    ac_no: Optional[str]
    balance: Optional[int]
    ac_type: Optional[int]
    ac_name: Optional[str]
    ac_pd_code: Optional[int]
    ac_cp_code: Optional[int]
    ac_status: Optional[int]
    ac_reg: Optional[int]
    ac_new_dt: Optional[str]
    ac_close_dt: Optional[str]
    ac_rm_reg: Optional[int]
    user_id: Optional[int]
