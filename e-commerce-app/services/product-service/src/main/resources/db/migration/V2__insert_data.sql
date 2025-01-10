-- Insert sample categories
INSERT INTO categories (id, name, description) VALUES
(1, 'Electronics', 'Electronic devices and accessories'),
(2, 'Books', 'Physical and digital books'),
(3, 'Clothing', 'Fashion apparel and accessories'),
(4, 'Home & Garden', 'Home decor and gardening supplies'),
(5, 'Sports & Outdoors', 'Sports equipment and outdoor gear');

-- Insert sample products
INSERT INTO products (id, name, description, price, available_quantity, category_id) VALUES
-- Electronics
(1, 'Smartphone X', 'Latest smartphone with advanced features', 799.99, 50, 1),
(2, 'Laptop Pro', '15-inch laptop with high performance', 1299.99, 30, 1),
(3, 'Wireless Earbuds', 'Premium wireless earbuds with noise cancellation', 149.99, 100, 1),

-- Books
(4, 'The Art of Programming', 'Comprehensive guide to programming', 49.99, 75, 2),
(5, 'Business Strategy', 'Modern business strategy handbook', 29.99, 60, 2),

-- Clothing
(6, 'Classic T-Shirt', 'Cotton t-shirt in various colors', 19.99, 200, 3),
(7, 'Denim Jeans', 'Comfortable slim-fit jeans', 59.99, 150, 3),

-- Home & Garden
(8, 'Garden Tools Set', 'Complete set of essential garden tools', 89.99, 40, 4),
(9, 'Smart LED Bulb', 'WiFi-enabled smart LED bulb', 24.99, 120, 4),

-- Sports & Outdoors
(10, 'Yoga Mat', 'Non-slip exercise yoga mat', 29.99, 80, 5),
(11, 'Tennis Racket', 'Professional tennis racket', 129.99, 25, 5),
(12, 'Running Shoes', 'Lightweight running shoes', 89.99, 60, 5);
