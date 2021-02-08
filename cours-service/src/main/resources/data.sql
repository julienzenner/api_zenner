INSERT INTO cours (id, titre, description, prix)
VALUES ('f44ec9f2-756f-4f67-98c6-a3557e3b0f70', 'Kubernetes', 'Orchestration de conteneur Docker avec Kubernetes', '50.5');
INSERT INTO cours (id, titre, description, prix)
VALUES ('a9bb838c-e20d-43e6-9392-0f1660340ec6', 'Docker', 'Conteunerisation de vos applications avec Docker', '0.0');
INSERT INTO cours (id, titre, description, prix)
VALUES ('e26f7c46-8db8-414c-b835-875b44c13859', 'SpringBoot', 'Création d''une architecture microservice avec SpringBoot', '9.99');

INSERT INTO cours_episodes_id (cours_id, episodes_id)
VALUES ('f44ec9f2-756f-4f67-98c6-a3557e3b0f70', 'bb28fa53-12e7-41a6-842c-daf3fde6f904');
INSERT INTO cours_episodes_id (cours_id, episodes_id)
VALUES ('f44ec9f2-756f-4f67-98c6-a3557e3b0f70', '79ddf9ed-7586-4039-b3d8-dcea821bdc4a');
INSERT INTO cours_episodes_id (cours_id, episodes_id)
VALUES ('f44ec9f2-756f-4f67-98c6-a3557e3b0f70', '614e2451-f51b-41a8-93f4-205f6a4e08d3');
INSERT INTO cours_episodes_id (cours_id, episodes_id)
VALUES ('f44ec9f2-756f-4f67-98c6-a3557e3b0f70', '62280ce0-4b44-42a7-9d96-f4c65669c416');
INSERT INTO cours_episodes_id (cours_id, episodes_id)
VALUES ('f44ec9f2-756f-4f67-98c6-a3557e3b0f70', '22d632c3-3ce8-455e-a302-5ddd21971769');

INSERT INTO cours_episodes_id (cours_id, episodes_id)
VALUES ('a9bb838c-e20d-43e6-9392-0f1660340ec6', 'd3caf2f5-4d5b-41df-a50a-5fb18e91ec34');
INSERT INTO cours_episodes_id (cours_id, episodes_id)
VALUES ('a9bb838c-e20d-43e6-9392-0f1660340ec6', '86f4ce46-3c58-456b-8439-28da791a243f');
INSERT INTO cours_episodes_id (cours_id, episodes_id)
VALUES ('a9bb838c-e20d-43e6-9392-0f1660340ec6', '4af16bee-5703-4080-91c5-13e650524d6b');

INSERT INTO cours_episodes_id (cours_id, episodes_id)
VALUES ('e26f7c46-8db8-414c-b835-875b44c13859', 'bcfa3749-62eb-4267-9022-976ee5b9f819');
INSERT INTO cours_episodes_id (cours_id, episodes_id)
VALUES ('e26f7c46-8db8-414c-b835-875b44c13859', 'b559fcbc-6908-4dff-8839-9f0e697ed2b9');
INSERT INTO cours_episodes_id (cours_id, episodes_id)
VALUES ('e26f7c46-8db8-414c-b835-875b44c13859', 'a77dcb40-df8c-4baa-9715-3ba457da4439');
INSERT INTO cours_episodes_id (cours_id, episodes_id)
VALUES ('e26f7c46-8db8-414c-b835-875b44c13859', '0fe8da4a-ca77-4727-8ecd-b5c7a402dba3');

INSERT INTO episode (id, titre, video)
VALUES ('bb28fa53-12e7-41a6-842c-daf3fde6f904', 'Introduction', 'https://www.youtube.com/watch?v=rmf04ylI2K0');
INSERT INTO episode (id, titre, video)
VALUES ('79ddf9ed-7586-4039-b3d8-dcea821bdc4a', 'Concepts de base', 'https://www.youtube.com/watch?v=WQrNv2r7Xaw');
INSERT INTO episode (id, titre, video)
VALUES ('614e2451-f51b-41a8-93f4-205f6a4e08d3', 'Concepts avancés', 'https://www.youtube.com/watch?v=oFglQ50O_rU');
INSERT INTO episode (id, titre, video)
VALUES ('62280ce0-4b44-42a7-9d96-f4c65669c416', 'Création d''un cluster', 'https://www.youtube.com/watch?v=6xJwQgDnMFE');
INSERT INTO episode (id, titre, video)
VALUES ('22d632c3-3ce8-455e-a302-5ddd21971769', 'Bonnes pratiques', 'https://www.youtube.com/watch?v=D-Pjnbaa_S4');
INSERT INTO episode (id, titre, video)
VALUES ('d3caf2f5-4d5b-41df-a50a-5fb18e91ec34', 'Introduction', 'https://www.youtube.com/watch?v=JSLpG_spOBM');
INSERT INTO episode (id, titre, video)
VALUES ('86f4ce46-3c58-456b-8439-28da791a243f', 'Dockerfile', 'https://www.youtube.com/watch?v=Ik_mC7JSJ-A');
INSERT INTO episode (id, titre, video)
VALUES ('4af16bee-5703-4080-91c5-13e650524d6b', 'Docker-compose', 'https://www.youtube.com/watch?v=Qw9zlE3t8Ko');
INSERT INTO episode (id, titre, video)
VALUES ('bcfa3749-62eb-4267-9022-976ee5b9f819', 'Création microservice', 'https://www.youtube.com/watch?v=WBaIpLNqYtE');
INSERT INTO episode (id, titre, video)
VALUES ('b559fcbc-6908-4dff-8839-9f0e697ed2b9', 'Communication entre service', 'https://www.youtube.com/watch?v=552Zf6ZE6GE');
INSERT INTO episode (id, titre, video)
VALUES ('a77dcb40-df8c-4baa-9715-3ba457da4439', 'Service discovery', 'https://www.youtube.com/watch?v=e09P-CkCvvs');
INSERT INTO episode (id, titre, video)
VALUES ('0fe8da4a-ca77-4727-8ecd-b5c7a402dba3', 'Messaging et les microservices : RabbitMQ', 'https://www.youtube.com/watch?v=o4qCdBR4gUM');