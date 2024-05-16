from flask import Flask, jsonify
from tensorflow.keras.models import load_model
from tensorflow.keras.preprocessing.image import load_img, img_to_array
import numpy as np
from flask import request
import os
import uuid
from fruit_data import fruits

IMAGE_WIDTH = 128
IMAGE_HEIGHT = 128
IMAGE_SIZE = (IMAGE_WIDTH, IMAGE_HEIGHT)
IMAGE_CHANNELS = 3
BATCH_SIZE = 32

classes = [
    "aloevera",
    "banana",
    "bilimbi",
    "cantaloupe",
    "cassava",
    "coconut",
    "corn",
    "cucumber",
    "curcuma",
    "eggplant",
    "galangal",
    "ginger",
    "guava",
    "kale",
    "longbeans",
    "mango",
    "melon",
    "orange",
    "paddy",
    "papaya",
    "peper chili",
    "pineapple",
    "pomelo",
    "shallot",
    "soybeans",
    "spinach",
    "sweet potatoes",
    "tobacco",
    "waterapple",
    "watermelon",
]
# classes = [
#     "bilimbi",
#     "mango",
#     "guava",
#     "orange",
#     "papaya",
#     "pineapple",
#     "sweet potatoes",
#     "waterapple",
#     "watermelon",
# ]
app = Flask(__name__)

model = load_model("./plant_type_classifier_all.keras")


@app.route("/", methods=["GET"])
def hello():
    return "Hello from the API", 200


@app.route("/get_fruit_data", methods=["POST"])
def get_fruit_name():
    # Check if a file was posted
    if "file" not in request.files:
        return jsonify({"error": "No file provided"}), 400

    file = request.files["file"]

    # Save the file temporarily
    temp_filename = str(uuid.uuid4()) + ".jpg"
    file.save(temp_filename)

    img = load_img(temp_filename, target_size=IMAGE_SIZE)

    # Convert the image to a numpy array
    img_array = img_to_array(img)
    img_array = np.expand_dims(img_array, axis=0)

    # Normalize the image (if your model expects normalized images)
    # img_array /= 255.0

    # Use the model to make a prediction
    prediction = model.predict(img_array)

    print(prediction)
    # Get the name of the fruit
    index = np.argmax(prediction)
    fruit_name = classes[index]
    print(fruit_name)

    fruit = ""

    for data in fruits.fruits:
        if data["id"] == fruit_name:
            fruit = data

    # Remove the temporary file
    os.remove(temp_filename)

    return jsonify(fruit), 200


if __name__ == "__main__":
    app.run(debug=True)
