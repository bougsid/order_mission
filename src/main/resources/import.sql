INSERT INTO `employeprofile` (`id`, `type`) VALUES ('1', 'ADMIN'),('2', 'USER');
INSERT INTO `app_user_user_profile` (`USER_ID`, `USER_PROFILE_ID`) VALUES ('1', '1'),('2', '2'),('3', '2'),('4', '2'),('5', '2');
INSERT INTO `dept` (`id`, `nom`, `chef_idEmploye`) VALUES ('1', 'DSAE', NULL), ('2', 'DLEC', NULL), ('3', 'DG', NULL), ('4', 'DAF', NULL), ('5', 'DR&D', NULL), ('6', 'DE', NULL), ('7', 'SI', NULL), ('8', 'SRH', NULL), ('9', 'CFA', NULL), ('10', 'CDC', NULL);
INSERT INTO `missiontype` (`id`, `label`, `id_dept`) VALUES ('1', 'Prospection', '1'), ('2', 'Accompagnement', '1'), ('3', 'Forume', NULL);
INSERT INTO `bank` (`id`, `name`, `phone`) VALUES ('1', 'Banque Populaire', '0650465456'), ('2', 'BMCE', '0552045405');
INSERT INTO `entreprise` (`id`, `nom`) VALUES (1, 'iam'), (2, 'inwi');
INSERT INTO `ville` (`id`, `nom`) VALUES ('1', 'CASA'), ('2', 'Mohammmedia');
INSERT INTO `grade` (`id`, `label`, `type`) VALUES (1, 'Derecteur Generale', 'DG'), (2, 'Chef de Service', 'CHEF'),(3, 'AUTRE', 'AUTRE'),(4, 'Assistant(e)', 'ASSISTANT'),(5, 'Prof', 'AUTRE');
INSERT INTO `employe` (`idEmploye`, `civilite`, `classe`, `email`, `fonction`, `matricule`, `nom`, `password`, `prenom`, `rib`, `id_bank`, `id_dept`, `id_grade`) VALUES (1, 'M', 'A', 'aaa@ll.ll', 'lll', 'admin', 'Admin', 'admin', 'Admin', '14444', NULL, NULL, NULL),(2, 'M', 'A', NULL, 'fff', '111', 'Ayoub', '111', 'BOUGSID', '456', 1, 3, 5),(3, 'M', 'A', NULL, 'fff', '222', 'Chef', '222', 'Service', '555', 1, 3, 2),(4, 'M', 'A', NULL, 'fff', '333', 'Chef', '333', 'Service', '55', 1, 1, 2),(5, 'M', 'A', NULL, 'fff', '444', 'Directeur', '444', 'Generale', '5555', 1, NULL, 1);
