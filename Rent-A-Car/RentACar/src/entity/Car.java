package entity;

public class Car {
        private int id;
        private int model_id;
        private Car.Color color;
        private int km;
        private String plate;
        private Model model;
        private Brand brand;

        public enum Color {
            RED,
            BLUE,
            WHITE,
            GREEN,
            AQUA
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getModel_id() {
            return model_id;
        }

        public void setModel_id(int model_id) {
            this.model_id = model_id;
        }

        public Color getColor() {
            return color;
        }

        public void setColor(Color color) {
            this.color = color;
        }

        public int getKm() {
            return km;
        }

        public void setKm(int km) {
            this.km = km;
        }

        public String getPlate() {
            return plate;
        }

        public void setPlate(String plate) {
            this.plate = plate;
        }

        public Model getModel() {
            return model;
        }

        public void setModel(Model model) {
            this.model = model;
        }

        public Brand getBrand() {
            return brand;
        }

        public void setBrand(Brand brand) {
            this.brand = brand;
        }

        @Override
        public String toString() {
            return "Car{" +
                    "id=" + id +
                    ", model_id=" + model_id +
                    ", color=" + color +
                    ", km=" + km +
                    ", plate='" + plate + '\'' +
                    ", model=" + model +
                    ", brand=" + brand +
                    '}';
        }
    }

