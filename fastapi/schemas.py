from typing import Optional
from pydantic import BaseModel
from datetime import date


class userReq(BaseModel):
    phone: Optional[str]


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


class FinanceOut(BaseModel):
    fn_name: str
    fn_logo: str
    fn_date: date
    open: int
    close: int
    high: int
    low: int
    volume: int
    per: float

    class Config:
        orm_mode = True
