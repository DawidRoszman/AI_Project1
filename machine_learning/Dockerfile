FROM python:3.9

RUN pip install flask waitress tensorflow

WORKDIR /opt

COPY fruits .
COPY app.py app.py

CMD ["waitress-serve", "--listen", "0.0.0.0:5000", "app:app"]
