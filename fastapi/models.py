from sqlalchemy.orm import declarative_base
from sqlalchemy import Column, Integer, String

Base = declarative_base()


class Item(Base):
    __tablename__ = 'item'
    id = Column(Integer, primary_key=True, index=True)
    username = Column(String(20))

    def __repr__(self):
        return "<Item(id='%s', username='%s')>" % (
            self.id,
            self.username
        )

    class Config:
        orm_mode = True