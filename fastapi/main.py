from fastapi import FastAPI, Response, status
from starlette.responses import JSONResponse
from fastapi.params import Depends
# from starlette.responses import RedirectResponse
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


# @app.get("/")    # 스웨거 확인용
# async def main():
#     return RedirectResponse("/docs/")


@app.get("/hello/{name}", name="제목입니다", description="문장입니다")
async def say_hello(name: str):
    return JSONResponse({"message": f"Hello {name}"})


@app.post("/data/v1/user/register", status_code=200)
async def test(user: schemas.userReq, response: Response, db: Session = Depends(get_db)):
    try:
        user_id = db.query(models.User).filter(models.User.phone == user.phone).first().id
        function.create(user_id, db)
    except:
        response.status_code = status.HTTP_409_CONFLICT

@app.get("/data/v1/random")
async def a():
    return JSONResponse({"message": "dddddddddd"})
