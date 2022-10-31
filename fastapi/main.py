from fastapi import FastAPI
from starlette.responses import JSONResponse
from typing import List
from fastapi.params import Depends
from starlette.responses import RedirectResponse
import models, schemas
from Connection import SessionLocal, engine
from sqlalchemy.orm import Session

models.Base.metadata.create_all(bind=engine)

app = FastAPI()


def get_db():
    try:
        db = SessionLocal()
        yield db
    finally:
        db.close()


@app.get("/")
async def main():
    return RedirectResponse("/docs/")


@app.get("/hello/{name}", name="제목입니다", description="문장입니다")
async def say_hello(name: str):
    return JSONResponse({"message": f"Hello {name}"})


@app.post("/items/")
async def create_users(item: schemas.Item, db: Session = Depends(get_db)):
    item = models.Item(username=item.username)
    db.add(item)
    db.commit()
    db.refresh(item)
