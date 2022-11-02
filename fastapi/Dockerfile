FROM python:3.9.5
WORKDIR /var/jenkins_home/workspace/data/data
COPY requirements.txt ./

RUN pip install --no-cache-dir --upgrade -r /var/jenkins_home/workspace/data/data/requirements.txt
COPY . .
CMD ["uvicorn", "app.main:app", "--host", "0.0.0.0", "--port", "8082"]