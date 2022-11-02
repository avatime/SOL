import pandas as pd
from sqlalchemy import select
from Connection import engine
import models
import random

cor_list = [[1, [3, 3, 6]], [2, [3, 6, 2, 3]], [3, [3, 4, 4, 2]], [4, [3, 6, 5]], [5, [4, 3, 6]], [6, [6, 2, 6]],
            [7, [6, 2, 6]], [8, [4, 2, 7]], [9, [3, 3, 6]], [11, [4, 2, 6, 1]], [12, [3, 6, 3]],
            [13, [3, 4, 4, 3]], [14, [3, 4, 4, 2]], [16, [3, 2, 6, 2]]]
team = ['민경욱', '김찬영', '이주영', '정건우', '이지나', '채윤선']
cor_name = [0, '신한은행', '기업은행', 'NH농협은행', '하나은행', '우리은행', '국민은행', '우체국',
            '카카오뱅크', '케이뱅크', 0, '새마을금고', '씨티은행', '산업은행',
            '경남은행', 0, '대구은행']
fi_name = ['한화투자', 'DB금융', 'KB증권', '다올투자증권', 'NH투자', 'SK증권', '교보증권', '대신증권',
           '메리츠증권', '미래에셋대우', '부국증권', '삼성증권', '신영증권', '신한금융',
           '유안타증권', '유진투자', '이베스트', '케이프투자', '키움증권', '하나금융',
           '하이투자', '현대차투자', '한국포스증권', '한국투자', '카카오페이증권']
td_list = ['요기요', '배달의민족', '네이버페이', '티머니', 'GS25', '바나프레소', '삼성디지털프라자', '이마트',
           '롯데마트', '하이마트', 'Google Play', 'CGV', '교촌치킨']
ac_pd_list = [0, [13], [22, 23, 24], [25, 26, 27], [28], [29, 30], [31, 32], [1, 35, 36, 37], [33],
              [34], [2], [3, 4, 5], [6, 7], [14, 15, 16], [8, 9], [10, 11, 12], [17, 18, 19, 20, 21]]


def create(user_id, db):
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
                ac_pd_code = random.choice(ac_pd_list[cor_num])
                name = cor_name[cor_num]
                if type == 2:    # 증권계좌 생성
                    cor_num = random.randint(32, 56)
                    name = fi_name[cor_num - 32]
                    ac_pd_code = 99
                new_ac.append(models.Account(ac_no=check, balance=random.randint(5, 500)*10000, ac_type=type,
                                             ac_name=name, ac_pd_code=ac_pd_code, ac_cp_code=cor_num,
                                             ac_status=10, ac_reg=0, ac_new_dt="2022-09-26", ac_close_dt="2023-01-01",
                                             ac_rm_reg=0, user_id=user_id))
                type += 1
                break
    db.add_all(new_ac)
    db.commit()
    db.refresh(new_ac[0])
    db.refresh(new_ac[1])
    db.refresh(new_ac[2])

    # step.2 카드 생성
    new_card = []
    # code 1~99 신용 / 100~199 체크
    for i in range(1, 3):
        code = random.randint(1, 99) if i == 1 else random.randint(100, 199)
        card_list = pd.read_sql_query(select(models.Card.cd_no), engine)['cd_no'].tolist()
        while 1:
            check = ''
            for _ in range(4):
                num = ''.join(random.sample(['1', '2', '3', '4', '5', '6', '7', '8', '9', '0'], 4))
                check = check + num + '-'
            check = check[:-1]
            if check not in card_list:
                break
        new_card.append(models.Card(cd_no=check, cd_pwd='0000', cd_mt_month='12', cd_mt_year='22',
                                    cd_pd_code=code, cd_status=10, cd_reg=0,
                                    user_id=user_id, account_ac_no=new_ac[0].ac_no))

    db.add_all(new_card)
    db.commit()
    db.refresh(new_card[0])
    db.refresh(new_card[1])

    # step.3 카드 결제 내역 생성
    card_his = []
    for i in range(2):    # 신용, 체크
        for day in ['2022-11-14', '2022-11-15', '2022-11-16', '2022-11-17', '2022-11-18']:
            name = random.choice(td_list)
            money = random.randint(5, 100) * 1000
            card_his.append(models.CardPaymentHistory(cd_py_dt=day, cd_py_name=name,
                                                      cd_val=money, cd_tp=1,
                                                      cd_no=new_card[i].cd_no))
    db.add_all(card_his)
    db.commit()
    for i in card_his:
        db.refresh(i)

    # stpe.4 거래 내역 생성
    name = db.query(models.User).filter(models.User.id == user_id).first().name
    trade = []
    # i = 5 부터는 체크카드 내역
    for i in range(10):
        td_val = random.randint(1, 100) * 1000 if i < 5 else card_his[i].cd_val
        td_dt = "2022-11-18" if i < 5 else card_his[i].cd_py_dt
        td_cn = random.choice(team) if i < 5 else card_his[i].cd_py_name
        td_type = random.randint(1, 2) if i < 5 else 2
        ac_no = new_ac[0].ac_no
        trade.append(models.TradeHistory(td_val=td_val, td_dt=td_dt, td_cn=td_cn,
                                         td_type=td_type, td_tg=td_cn, td_tg_ac=None,
                                         td_rec=td_cn, td_sed=name, ac_no=ac_no))
    db.add_all(trade)
    db.commit()
    for i in trade:
        db.refresh(i)

    # step.5 보험 1개 생성
    ins = models.Insurance(is_reg_dt='2022-09-26', is_clo_dt=None, is_mat_dt='2022-11-25',
                           is_status=10, is_pd_code=random.randint(1, 6),
                           is_name=name, is_reg=0, fee=35000, user_id=user_id,
                           ac_no=new_ac[2].ac_no)
    db.add(ins)
    db.commit()
    db.refresh(ins)