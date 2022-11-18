FROM python:3.7
WORKDIR /var/jenkins_home/workspace/data/fastapi
COPY requirements.txt ./

RUN pip install --no-cache-dir --upgrade -r /var/jenkins_home/workspace/data/fastapi/requirements.txt
COPY . .
CMD ["uvicorn", "main:app", "--host", "0.0.0.0", "--port", "8082"]