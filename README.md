# Foodlicious

## Wstęp

Aplikacja pozwala rozpoznawać owoce, rośliny oraz warzywa ze zdjęcia

## Opis

Aplikacji można używać poprzez aplikację na telefon lub po przez API.

Model został nauczyony na podstawie danych 

https://www.kaggle.com/datasets/yudhaislamisulistya/plants-type-datasets/data

Na danych testowych model miał poprawność 90%.

## Przykład użycia

Korzystanie z API

```bash
curl -X POST -F "file=@./split_ttv_dataset_type_of_plants/watermelon/aug_0_1035.jpg" https://foodlicious.dawidroszman.eu/get_fruit_data
```
Link do aplikacji mobilnej

https://drive.google.com/file/d/1iLaqfONeBzUW4j9VoRrQ0SzO34e1eKbV/view?usp=sharing
