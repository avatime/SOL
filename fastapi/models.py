from sqlalchemy.orm import declarative_base
from sqlalchemy import Column, Integer, String, BigInteger, Boolean, Date, LargeBinary, ForeignKey, Float

Base = declarative_base()


class Account(Base):
    __tablename__ = 'account'
    ac_no = Column(String(30), primary_key=True)
    balance = Column(Integer)
    ac_type = Column(Integer)
    ac_name = Column(String(30))
    ac_pd_code = Column(Integer)
    ac_cp_code = Column(BigInteger)
    ac_status = Column(Integer)
    ac_reg = Column(Boolean)
    ac_new_dt = Column(Date)
    ac_close_dt = Column(Date)
    ac_rm_reg = Column(Boolean)
    user_id = Column(LargeBinary, ForeignKey('user.id'))

    def __repr__(self):
        return "<계좌는~(ac_no='%s', balance='%s', ac_type='%s', ac_name='%s', ac_pd_code='%s', ac_cp_code='%s', " \
               "ac_new_dt='%s')>" % (
                   self.ac_no, self.balance, self.ac_type, self.ac_name, self.ac_pd_code, self.ac_cp_code,
                   self.ac_new_dt

               )


class User(Base):
    __tablename__ = 'user'
    id = Column(LargeBinary, primary_key=True)
    name = Column(String(30))
    phone = Column(String(255))


class TradeHistory(Base):
    __tablename__ = 'trade_history'
    id = Column(BigInteger, primary_key=True, index=True)
    td_val = Column(Integer)
    td_dt = Column(Date)
    td_cn = Column(String(30))
    td_type = Column(Integer)
    td_tg = Column(String(30))
    td_tg_ac = Column(String(30))
    td_rec = Column(String(30))
    td_sed = Column(String(30))
    ac_no = Column(String, ForeignKey('account.ac_no'))


class Card(Base):
    __tablename__ = 'card'
    cd_no = Column(String(30), primary_key=True, index=True)
    cd_pwd = Column(String(4))
    cd_mt_month = Column(String(2))
    cd_mt_year = Column(String(2))
    cd_pd_code = Column(BigInteger)
    cd_status = Column(Integer)
    cd_reg = Column(Boolean)
    user_id = Column(String(30), ForeignKey('user.id'))
    account_ac_no = Column(String(30))

    def __repr__(self):
        return "<카드(cd_no='%s', cd_pwd='%s', cd_pd_code='%s', ac_no='%s')>" % (
                   self.cd_no, self.cd_pwd, self.cd_pd_code, self.ac_no
               )


class CardPaymentHistory(Base):
    __tablename__ = 'card_payment_history'
    id = Column(BigInteger, primary_key=True, index=True)
    cd_py_dt = Column(Date)
    cd_py_name = Column(String(30))
    cd_val = Column(Integer)
    cd_tp = Column(Integer)
    cd_no = Column(String(30), ForeignKey('card.cd_no'))


class Insurance(Base):
    __tablename__ = 'insurance'
    id = Column(BigInteger, primary_key=True, index=True)
    is_reg_dt = Column(Date)
    is_clo_dt = Column(Date)
    is_mat_dt = Column(Date)
    is_status = Column(Integer)
    is_pd_code = Column(BigInteger)
    is_name = Column(String(30))
    is_reg = Column(Boolean)
    fee = Column(Integer)
    user_id = Column(LargeBinary, ForeignKey('user.id'))
    ac_no = Column(String(30), ForeignKey('account.ac_no'))


class Finance(Base):
    __tablename__ = 'finance'
    id = Column(BigInteger, primary_key=True, index=True)
    fn_name = Column(String(12))
    fn_date = Column(Date)
    fn_close = Column(Integer)
    fn_per = Column(Float)

    def __repr__(self):
        return "<기업명='%s', 날짜='%s', 가격='%s'>" % (
                   self.fn_name, self.fn_date, self.fn_close
               )