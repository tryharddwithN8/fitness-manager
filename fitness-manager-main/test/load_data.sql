
USE fitness_db;

INSERT INTO users (username, password, email, role) VALUES
('admin_user', 'admin_password', 'admin@example.com', 'admin');

INSERT INTO users (username, password, email, role) VALUES
('john_doe', 'password123', 'john.doe@example.com', 'coach'),
('jane_smith', 'password123', 'jane.smith@example.com', 'coach'),
('michael_johnson', 'password123', 'michael.johnson@example.com', 'coach'),
('sarah_connor', 'password123', 'sarah.connor@example.com', 'coach'),
('emily_clark', 'password123', 'emily.clark@example.com', 'coach');


INSERT INTO users (username, password, email, role) VALUES
('alice_wonderland', 'password123', 'alice.wonderland@example.com', 'user'),
('bob_builder', 'password123', 'bob.builder@example.com', 'user'),
('charlie_brown', 'password123', 'charlie.brown@example.com', 'user'),
('david_beckham', 'password123', 'david.beckham@example.com', 'user'),
('ella_fitzgerald', 'password123', 'ella.fitzgerald@example.com', 'user'),
('frank_sinatra', 'password123', 'frank.sinatra@example.com', 'user'),
('george_clooney', 'password123', 'george.clooney@example.com', 'user'),
('hannah_montana', 'password123', 'hannah.montana@example.com', 'user'),
('ian_malcolm', 'password123', 'ian.malcolm@example.com', 'user'),
('julia_child', 'password123', 'julia.child@example.com', 'user'),
('kevin_spacey', 'password123', 'kevin.spacey@example.com', 'user'),
('lisa_simpson', 'password123', 'lisa.simpson@example.com', 'user'),
('mike_tyson', 'password123', 'mike.tyson@example.com', 'user'),
('nina_simone', 'password123', 'nina.simone@example.com', 'user'),
('oliver_twist', 'password123', 'oliver.twist@example.com', 'user'),
('paul_rudd', 'password123', 'paul.rudd@example.com', 'user'),
('quinn_fabray', 'password123', 'quinn.fabray@example.com', 'user'),
('rachel_green', 'password123', 'rachel.green@example.com', 'user'),
('sam_smith', 'password123', 'sam.smith@example.com', 'user'),
('tina_turner', 'password123', 'tina.turner@example.com', 'user'),
('uma_thurman', 'password123', 'uma.thurman@example.com', 'user'),
('vicky_krieps', 'password123', 'vicky.krieps@example.com', 'user'),
('will_smith', 'password123', 'will.smith@example.com', 'user'),
('xena_warrior', 'password123', 'xena.warrior@example.com', 'user'),
('yoda_jedi', 'password123', 'yoda.jedi@example.com', 'user'),
('zach_snyder', 'password123', 'zach.snyder@example.com', 'user'),
('aaron_rodgers', 'password123', 'aaron.rodgers@example.com', 'user'),
('brad_pitt', 'password123', 'brad.pitt@example.com', 'user'),
('cameron_diaz', 'password123', 'cameron.diaz@example.com', 'user'),
('daniel_radcliffe', 'password123', 'daniel.radcliffe@example.com', 'user'),
('ellen_degeneres', 'password123', 'ellen.degeneres@example.com', 'user'),
('floyd_mayweather', 'password123', 'floyd.mayweather@example.com', 'user');


INSERT INTO coaches (user_id, bio, experience) VALUES
(2, 'Fitness trainer specializing in strength and conditioning.', '5 years of experience in personal training.'),
(3, 'Yoga instructor with a passion for wellness and mindfulness.', '8 years of experience in yoga and wellness coaching.'),
(4, 'Expert in nutrition and diet planning for fitness.', '6 years of experience in nutrition coaching.'),
(5, 'Personal trainer focusing on cardio and endurance training.', '7 years of experience in endurance sports coaching.'),
(6, 'Certified fitness coach with expertise in bodyweight training.', '4 years of experience in bodyweight fitness training.');
