from sqlalchemy import create_engine
from sqlalchemy.ext.declarative import declarative_base
from sqlalchemy.orm import sessionmaker


URL = "mysql+mysqlconnector://ssafy:wkdbf77!@k7a403.p.ssafy.io:3306/tmp"
engine = create_engine(URL)
SessionLocal = sessionmaker(autocommit=False, autoflush=False, bind=engine)
Base = declarative_base()