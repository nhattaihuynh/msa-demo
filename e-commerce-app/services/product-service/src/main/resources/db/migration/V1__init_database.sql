-- Create sequences
CREATE SEQUENCE categories_seq START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE products_seq START WITH 1 INCREMENT BY 1;

-- Create categories table
CREATE TABLE categories (
    id INTEGER PRIMARY KEY DEFAULT nextval('categories_seq'),
    name VARCHAR(100) NOT NULL,
    description TEXT
);

-- Create products table
CREATE TABLE products (
    id INTEGER PRIMARY KEY DEFAULT nextval('products_seq'),
    name VARCHAR(200) NOT NULL,
    description TEXT,
    price NUMERIC(38,2) NOT NULL,
    available_quantity INTEGER NOT NULL DEFAULT 0,
    category_id INTEGER,
    FOREIGN KEY (category_id) REFERENCES categories(id)
);

-- Create indexes for better query performance
CREATE INDEX idx_products_category ON products(category_id);