from sqlalchemy import create_engine
from sqlalchemy.ext.declarative import declarative_base
from sqlalchemy.orm import sessionmaker
from pydantic import BaseSettings


class Settings(BaseSettings):
    USERNAME: str
    PASSWORD: str

    class Config:
        env_file = '.env'


settings = Settings()
URL = f"mysql+mysqlconnector://{settings.USERNAME.lower()}:{settings.PASSWORD}@k7a403.p.ssafy.io:3306/finance_db"
engine = create_engine(URL, echo=True)
# SessionLocal = sessionmaker(autocommit=False, autoflush=False, bind=engine)
SessionLocal = sessionmaker(autocommit=False, autoflush=False)
Base = declarative_base()
