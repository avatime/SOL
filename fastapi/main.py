from fastapi import FastAPI, Response, status
from fastapi.params import Depends
from sqlalchemy.orm import Session
# from starlette.responses import RedirectResponse
from apscheduler.schedulers.background import BackgroundScheduler
import function
import models
import schemas
from Connection import SessionLocal, engine
from finance import finance_create

# models.Base.metadata.create_all(bind=engine)
models.Base.metadata.bind = engine
app = FastAPI()
s = BackgroundScheduler(timezone='Asia/Seoul')
s.add_job(finance_create, 'cron', [engine], hour='9', minute='10')
s.start()


def get_db():
    try:
        db = SessionLocal()
        yield db
    finally:
        db.close()


# @app.get("/")    # 스웨거 확인용
# async def main():
#     return RedirectResponse("/docs/")


@app.post("/data/v1/user/register", status_code=200)
async def test(user: schemas.userReq, response: Response, db: Session = Depends(get_db)):
    try:
        user_id = db.query(models.User).filter(models.User.phone == user.phone).first().id
        function.create(user_id, db)

    except:
        response.status_code = status.HTTP_409_CONFLICT
