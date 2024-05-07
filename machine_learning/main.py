import random
import os
from shutil import copyfile


from tensorflow.keras.preprocessing.image import ImageDataGenerator
from tensorflow.keras.models import Sequential
from tensorflow.keras.layers import Conv2D, MaxPooling2D, Flatten, Dense, Dropout
from tensorflow.keras.optimizers import Adam
from tensorflow.keras.callbacks import EarlyStopping


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


# train_dir = "./split_ttv_dataset_type_of_plants/Train_Set_Folder/"
# validate_dir = "./split_ttv_dataset_type_of_plants/Validation_Set_Folder/"
# test_dir = "./split_ttv_dataset_type_of_plants/Test_Set_Folder/"
#
# train_dir_filenames = [os.listdir(train_dir + x) for x in classes]
# validate_dir_filenames = [os.listdir(validate_dir + x) for x in classes]
# test_dir_filenames = [os.listdir(test_dir + x) for x in classes]
#
# print(validate_dir_filenames)

dataset_root = "split_ttv_dataset_type_of_plants"
train_split = 0.8  # 80% for training, 20% for testing

# Create directories for train and test data
train_data_dir = "train"
test_data_dir = "test"
os.makedirs(train_data_dir, exist_ok=True)
os.makedirs(test_data_dir, exist_ok=True)

for plant_category in os.listdir(dataset_root):
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
model.add(Dense(len(train_generator.class_indices), activation="softmax"))


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
epochs = 100  # You can adjust this number based on your needs
history = model.fit(
    train_generator,
    epochs=epochs,
    validation_data=test_generator,
    callbacks=[early_stopping],
)


# Evaluate the model
test_loss, test_acc = model.evaluate(test_generator)
print("Test accuracy:", test_acc)


# Save the model
model.save("plant_type_classifier.h5")
