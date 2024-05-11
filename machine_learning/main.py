import random
import os
from shutil import copyfile

from tensorflow.keras.preprocessing.image import load_img, img_to_array
import numpy as np

import tensorflow as tf
from tensorflow.keras.preprocessing.image import ImageDataGenerator
from tensorflow.keras.models import Sequential
from tensorflow.keras.layers import Conv2D, MaxPooling2D, Flatten, Dense, Dropout
from tensorflow.keras.optimizers import Adam
from tensorflow.keras.callbacks import EarlyStopping
from tensorflow.keras.models import load_model


IMAGE_WIDTH = 128
IMAGE_HEIGHT = 128
IMAGE_SIZE = (IMAGE_WIDTH, IMAGE_HEIGHT)
IMAGE_CHANNELS = 3
BATCH_SIZE = 32

classes = [
    "bilimbi",
    "mango",
    "guava",
    "orange",
    "papaya",
    "pineapple",
    "sweet potatoes",
    "waterapple",
    "watermelon",
]

dataset_root = "split_ttv_dataset_type_of_plants"
train_split = 0.7

# Create directories for train and test data
train_data_dir = "train"
test_data_dir = "test"
os.makedirs(train_data_dir, exist_ok=True)
os.makedirs(test_data_dir, exist_ok=True)

for t in os.listdir(dataset_root):
    for plant_category in os.listdir(os.path.join(dataset_root, t)):
        if os.path.isdir(os.path.join(dataset_root, plant_category)):
            print(plant_category)
            if plant_category not in classes:
                continue
            images = os.listdir(os.path.join(dataset_root, plant_category))
            num_images = len(images)
            num_train = int(train_split * num_images)

            # Shuffle the images
            random.shuffle(images)

            # Copy images to train and test directories
            for i, image in enumerate(images):
                src = os.path.join(dataset_root, plant_category, image)
                if i < num_train:
                    dst = os.path.join(train_data_dir, plant_category, image)
                else:
                    dst = os.path.join(test_data_dir, plant_category, image)
                os.makedirs(os.path.dirname(dst), exist_ok=True)
                copyfile(src, dst)

train_dir = "./train"
test_dir = "./test"
# Create data generators for training and testing
train_datagen = ImageDataGenerator(
    rescale=1.0 / 255,  # Normalize pixel values to [0, 1]
    rotation_range=30,
    width_shift_range=0.2,
    height_shift_range=0.2,
    shear_range=0.2,
    zoom_range=0.2,
    horizontal_flip=True,
    fill_mode="nearest",
)


test_datagen = ImageDataGenerator(rescale=1.0 / 255)

train_generator = train_datagen.flow_from_directory(
    train_dir,
    target_size=(IMAGE_WIDTH, IMAGE_HEIGHT),
    batch_size=BATCH_SIZE,
    class_mode="categorical",
)

test_generator = test_datagen.flow_from_directory(
    test_dir,
    target_size=(IMAGE_WIDTH, IMAGE_HEIGHT),
    batch_size=BATCH_SIZE,
    class_mode="categorical",
)

print(len(train_generator.class_indices))

# Create the CNN model
model = Sequential()

model.add(
    Conv2D(32, (3, 3), activation="relu", input_shape=(IMAGE_WIDTH, IMAGE_HEIGHT, 3))
)
model.add(MaxPooling2D(2, 2))
model.add(Conv2D(64, (3, 3), activation="relu"))
model.add(MaxPooling2D(2, 2))
model.add(Conv2D(128, (3, 3), activation="relu"))
model.add(MaxPooling2D(2, 2))
model.add(Conv2D(128, (3, 3), activation="relu"))
model.add(MaxPooling2D(2, 2))
model.add(Flatten())
model.add(Dense(512, activation="relu"))
model.add(Dropout(0.5))
model.add(Dense(9, activation="softmax"))


model.compile(
    optimizer=Adam(learning_rate=0.0001),
    loss="categorical_crossentropy",
    metrics=["accuracy"],
)


# Define the EarlyStopping callback
early_stopping = EarlyStopping(
    monitor="val_loss", patience=5, restore_best_weights=True
)


# Train the model with early stopping
# epochs = 1
# history = model.fit(
#     train_generator,
#     epochs=epochs,
#     validation_data=test_generator,
#     callbacks=[early_stopping],
# )
# Save the model
# model.save("plant_type_classifier.keras")

# Load model
model = load_model("./plant_type_classifier.keras")

# Evaluate the model
# test_loss, test_acc = model.evaluate(test_generator)
# print("Test accuracy:", test_acc)


# Load the image
img_path = "./split_ttv_dataset_type_of_plants/waterapple/aug_0_7674.jpg"
img = load_img(
    img_path, target_size=IMAGE_SIZE
)  # replace with the input size of your model

# Convert the image to a numpy array
img_array = img_to_array(img)
img_array = np.expand_dims(img_array, axis=0)

# Normalize the image (if your model expects normalized images)
img_array /= 255.0

# Use the model to make a prediction
prediction = model.predict(img_array)

# Print the prediction
print(prediction)

index = np.argmax(prediction)
fruit_name = classes[index]
print(fruit_name)
