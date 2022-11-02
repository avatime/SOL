import pandas as pd
from datetime import datetime, timedelta
from pandas_datareader import data as pdr
import sqlalchemy


start = (datetime.today() - timedelta(8)).strftime('%Y-%m-%d')
code_list = [['카카오', '035720.KS'], ['삼성전자', '005930.KS'], ['카카오뱅크', '323410.KS'], ['롯데정보통신', '286940.KS'],
             ['네이버', '035420.KS'], ['SK하이닉스', '000660.KS'], ['LG전자', '066570.KS'], ['현대차', '005380.KS'],
             ['기아', '000270.KS'], ['KT&G', '033780.KS']]


def finance_create(engine):
    finance = pd.DataFrame()

    for name, code in code_list:
        temp = pdr.get_data_yahoo(code, start=start)
        temp['fn_date'] = temp.index.strftime('%Y-%m-%d')
        temp['fn_name'] = name
        temp['fn_close'] = temp['Close']
        temp['fn_per'] = round(((temp.iloc[6, 3] - temp.iloc[5, 3]) / temp.iloc[6, 3]) * 100, 2)
        finance = pd.concat([finance, temp[['fn_name', 'fn_date', 'fn_close', 'fn_per']]], ignore_index=True)

    finance['id'] = finance.index + 1

    dtypesql = {
        'id': sqlalchemy.types.Integer(),
        'fn_name': sqlalchemy.types.VARCHAR(12),
        'fn_date': sqlalchemy.types.Date(),
        'fn_close': sqlalchemy.types.Integer(),
        'fn_per': sqlalchemy.types.Float()
    }
    finance.to_sql(name='finance', con=engine, if_exists='replace', index=False, dtype=dtypesql)