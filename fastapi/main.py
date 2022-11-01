from fastapi import FastAPI
from starlette.responses import JSONResponse
from fastapi.params import Depends
from starlette.responses import RedirectResponse
import models, schemas
from Connection import SessionLocal, engine
from sqlalchemy.orm import Session
import function

# models.Base.metadata.create_all(bind=engine)
models.Base.metadata.bind = engine

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


@app.post("/user/register")
async def test(user: schemas.userReq, db: Session = Depends(get_db)):
    # user_id = b'1\x00\x00\x00\x00\x00\x00\x00\x00\x00\x00\x00\x00\x00\x00\x00'
    function.create(user.user_id, db)
