
USE fitness_db;

INSERT INTO users (username, password, email, role, address) VALUES
('admin_user', 'admin_password', 'admin@example.com', 'admin', '123 Admin St, City'),

('john_doe', 'password123', 'john.doe@example.com', 'coach', '456 Coach Ave, City'),
('jane_smith', 'password123', 'jane.smith@example.com', 'coach', '789 Trainer Blvd, City'),
('michael_johnson', 'password123', 'michael.johnson@example.com', 'coach', '321 Fitness Rd, City'),
('sarah_connor', 'password123', 'sarah.connor@example.com', 'coach', '654 Workout Ln, City'),
('emily_clark', 'password123', 'emily.clark@example.com', 'coach', '987 Strength Pl, City'),

('alice_wonderland', 'password123', 'alice.wonderland@example.com', 'user', '111 Dreamland Dr, City'),
('bob_builder', 'password123', 'bob.builder@example.com', 'user', '222 Builder St, City'),
('charlie_brown', 'password123', 'charlie.brown@example.com', 'user', '333 Peanuts Ave, City'),
('david_beckham', 'password123', 'david.beckham@example.com', 'user', '444 Soccer Ln, City'),
('ella_fitzgerald', 'password123', 'ella.fitzgerald@example.com', 'user', '555 Jazz Blvd, City'),
('frank_sinatra', 'password123', 'frank.sinatra@example.com', 'user', '666 Swing Rd, City'),
('george_clooney', 'password123', 'george.clooney@example.com', 'user', '777 Actor Pl, City'),
('hannah_montana', 'password123', 'hannah.montana@example.com', 'user', '888 Pop Ave, City'),
('ian_malcolm', 'password123', 'ian.malcolm@example.com', 'user', '999 Dino Dr, City'),
('julia_child', 'password123', 'julia.child@example.com', 'user', '101 Chef St, City'),
('kevin_spacey', 'password123', 'kevin.spacey@example.com', 'user', '202 Drama Ln, City'),
('lisa_simpson', 'password123', 'lisa.simpson@example.com', 'user', '303 Cartoon Blvd, City'),
('mike_tyson', 'password123', 'mike.tyson@example.com', 'user', '404 Boxing Rd, City'),
('nina_simone', 'password123', 'nina.simone@example.com', 'user', '505 Soul Pl, City'),
('oliver_twist', 'password123', 'oliver.twist@example.com', 'user', '606 Orphan Ln, City'),
('paul_rudd', 'password123', 'paul.rudd@example.com', 'user', '707 Antman Dr, City'),
('quinn_fabray', 'password123', 'quinn.fabray@example.com', 'user', '808 Cheer St, City'),
('rachel_green', 'password123', 'rachel.green@example.com', 'user', '909 Friends Ave, City'),
('sam_smith', 'password123', 'sam.smith@example.com', 'user', '1010 Music Blvd, City'),
('tina_turner', 'password123', 'tina.turner@example.com', 'user', '1111 Rock Ln, City'),
('uma_thurman', 'password123', 'uma.thurman@example.com', 'user', '1212 KillBill Dr, City'),
('vicky_krieps', 'password123', 'vicky.krieps@example.com', 'user', '1313 Phantom Pl, City'),
('will_smith', 'password123', 'will.smith@example.com', 'user', '1414 FreshPrince St, City'),
('xena_warrior', 'password123', 'xena.warrior@example.com', 'user', '1515 Warrior Ave, City'),
('yoda_jedi', 'password123', 'yoda.jedi@example.com', 'user', '1616 Force Ln, City'),
('zach_snyder', 'password123', 'zach.snyder@example.com', 'user', '1717 Director Blvd, City'),
('aaron_rodgers', 'password123', 'aaron.rodgers@example.com', 'user', '1818 Packers Pl, City'),
('brad_pitt', 'password123', 'brad.pitt@example.com', 'user', '1919 Hollywood Blvd, City'),
('cameron_diaz', 'password123', 'cameron.diaz@example.com', 'user', '2020 Star Ln, City'),
('daniel_radcliffe', 'password123', 'daniel.radcliffe@example.com', 'user', '2121 Potter Dr, City'),
('ellen_degeneres', 'password123', 'ellen.degeneres@example.com', 'user', '2222 TalkShow Ave, City'),
('floyd_mayweather', 'password123', 'floyd.mayweather@example.com', 'user', '2323 Boxing Pl, City');



INSERT INTO coaches (user_id, bio, experience) VALUES
(2, 'Fitness trainer specializing in strength and conditioning.', '5 years of experience in personal training.'),
(3, 'Yoga instructor with a passion for wellness and mindfulness.', '8 years of experience in yoga and wellness coaching.'),
(4, 'Expert in nutrition and diet planning for fitness.', '6 years of experience in nutrition coaching.'),
(5, 'Personal trainer focusing on cardio and endurance training.', '7 years of experience in endurance sports coaching.'),
(6, 'Certified fitness coach with expertise in bodyweight training.', '4 years of experience in bodyweight fitness training.');
