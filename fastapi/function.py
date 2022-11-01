import pandas as pd
from sqlalchemy import select
from Connection import engine
import models
import random

cor_list = [[1, [3, 3, 6]], [2, [3, 6, 2, 3]], [3, [3, 4, 4, 2]], [4, [3, 6, 5]], [5, [4, 3, 6]], [6, [6, 2, 6]],
            [7, [6, 2, 6]], [8, [4, 2, 7]], [9, [3, 3, 6]], [11, [4, 2, 6, 1]], [12, [3, 6, 3]],
            [13, [3, 4, 4, 3]], [14, [3, 4, 4, 2]], [16, [3, 2, 6, 2]]]
cor_name = [0, '신한은행', '기업은행', 'NH농협은행', '하나은행', '우리은행', '국민은행', '우체국',
            '카카오뱅크', '케이뱅크', 0, '새마을금고', '씨티은행', '산업은행',
            '경남은행', 0, '대구은행']



def create(user_id, db):
    temp = b'1\x00\x00\x00\x00\x00\x00\x00\x00\x00\x00\x00\x00\x00\x00\x00'
    # step.1 계좌 생성
    ac_list = pd.read_sql_query(select(models.Account.ac_no), engine)['ac_no'].tolist()
    new_cor = random.sample(cor_list, 3)
    new_ac = []
    type = 1
    for i in new_cor:
        cor_num, role = i
        while 1:
            check = ''
            for x in role:
                num = ''.join(random.sample(['1', '2', '3', '4', '5', '6', '7', '8', '9', '0'], x))
                check = check + num + '-'
            check = check[:-1]
            if check not in ac_list:
                new_ac.append(models.Account(ac_no=check, balance=random.randint(10, 500)*10000, ac_type=type,
                                             ac_name=cor_name[cor_num], ac_pd_code=1, ac_cp_code=cor_num,
                                             ac_status=10, ac_reg=0, ac_new_dt="2022-09-26", ac_close_dt="2023-01-01",
                                             ac_rm_reg=0, user_id=temp))
                type += 1
                break
    print(new_ac)

create(1, 1)
