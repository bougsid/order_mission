INSERT INTO `employe` (`idEmploye`, `civilite`, `classe`, `derictor`, `email`, `fonction`, `matricule`, `nom`, `password`, `prenom`, `rib`, `id_bank`, `id_dept`, `id_grade`, `vehicule_id`) VALUES ('100', 'M', NULL, b'0', NULL, NULL, 'admin', 'Administrateur', 'admin', '', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `employeprofile` (`id`, `type`) VALUES ('1', 'ADMIN'),('2', 'USER');
INSERT INTO `app_user_user_profile` (`USER_ID`, `USER_PROFILE_ID`) VALUES ('100', '1');
INSERT INTO `dept` (`id`, `nom`, `chef_idEmploye`) VALUES ('1', 'DSAE', NULL), ('2', 'DLEC', NULL), ('3', 'DG', NULL), ('4', 'DAF', NULL), ('5', 'DR&D', NULL), ('6', 'DE', NULL), ('7', 'SI', NULL), ('8', 'SRH', NULL), ('9', 'CFA', NULL), ('10', 'CDC', NULL);
INSERT INTO `grade` (`id`, `label`, `type`) VALUES (1, 'Derecteur Generale', 'DG'), (2, 'Chef de Service', 'CHEF'),(3, 'Assistant(e)', 'ASSISTANT'),(4, 'AUTRE', 'AUTRE');
