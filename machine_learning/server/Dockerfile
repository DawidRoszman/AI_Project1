FROM python:3.9

RUN pip install flask waitress tensorflow Pillow

WORKDIR /opt

COPY fruit_data fruit_data
COPY plant_type_classifier_all.keras .
COPY app.py .

CMD ["waitress-serve", "--listen", "0.0.0.0:5000", "app:app"]
