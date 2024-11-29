import { MenuItem } from './menu.model';

export const AdminMenu: MenuItem[] = [
  {
    label: 'Main',
    isTitle: true,
  },

  {
    label: 'Dashboard',
    icon: 'home',
    link: '/dashboard',
  },

  {
    label: 'User management',
    isTitle: true,
  },

  {
    label: 'Users',
    icon: 'users',
    link: '/users/list-user',
  },

  {
    label: 'Add User',
    icon: 'user-check',
    link: '/users/add-user',
  },

  {
    label: 'Company management',
    isTitle: true,
  },

  {
    label: 'Liste Departements',
    icon: 'folder',
    link: '/company/list-modules',
  },

  {
    label: 'Add departements',
    icon: 'plus',
    link: '/company/add-module',
  },

  {
    label: 'Users requests',
    isTitle: true,
  },

  {
    label: 'Attestation',
    icon: 'navigation',
    link: '/website/attestation',
  },

  {
    label: 'Conge',
    icon: 'navigation',
    link: '/website/conge',
  },

  {
    label: 'Assurance',
    icon: 'navigation',
    link: '/website/assurance',
  },
  {
    label: 'Avance',
    icon: 'navigation',
    link: '/website/avance',
  },
  {
    label: 'fiche de paie',
    icon: 'navigation',
    link: '/website/fichep',
  },
];

export const ChefMenu: MenuItem[] = [
  {
    label: 'Main',
    isTitle: true,
  },

  {
    label: 'Dashboard',
    icon: 'home',
    link: '/dashboard',
  },

  {
    label: 'User management',
    isTitle: true,
  },

  {
    label: 'Users',
    icon: 'users',
    link: '/users/list-user',
  },

  {
    label: 'Chat',
    isTitle: true,
  },

  {
    label: 'Departement Chat',
    icon: 'chat',
    link: '/company/chat',
  },

  {
    label: 'Users requests',
    isTitle: true,
  },

  {
    label: 'Attestation',
    icon: 'navigation',
    link: '/website/attestation',
  },

  {
    label: 'Conge',
    icon: 'navigation',
    link: '/website/conge',
  },

  {
    label: 'Assurance',
    icon: 'navigation',
    link: '/website/assurance',
  },
  {
    label: 'Avance',
    icon: 'navigation',
    link: '/website/avance',
  },
  {
    label: 'fiche de paie',
    icon: 'navigation',
    link: '/website/fichep',
  },
  {
    label: 'Add a request',
    isTitle: true,
  },

  {
    label: 'Attestation',
    icon: 'navigation',
    link: '/website/demandeattestation',
  },

  {
    label: 'Conge',
    icon: 'navigation',
    link: '/website/demandeconge',
  },

  {
    label: 'Assurance',
    icon: 'navigation',
    link: '/website/demandeassurance',
  },
  {
    label: 'Avance',
    icon: 'navigation',
    link: '/website/demandeavance',
  },
  {
    label: 'fiche de paie',
    icon: 'navigation',
    link: '/website/demandefichedep',
  },
  {
    label: 'Personal Demandes',
    isTitle: true,
  },

  {
    label: 'Mes Demandes',
    icon: 'navigation',
    link: '/website/mes-demandes',
  },
];

export const UserMenu: MenuItem[] = [
  {
    label: 'Main',
    isTitle: true,
  },

  {
    label: 'Mes Demandes',
    icon: 'home',
    link: '/website/mes-demandes',
  },
  {
    label: 'Chat',
    isTitle: true,
  },

  {
    label: 'Departement Chat',
    icon: 'chat',
    link: '/company/chat',
  },

  {
    label: 'Add a request',
    isTitle: true,
  },

  {
    label: 'Attestation',
    icon: 'navigation',
    link: '/website/demandeattestation',
  },

  {
    label: 'Conge',
    icon: 'navigation',
    link: '/website/demandeconge',
  },

  {
    label: 'Assurance',
    icon: 'navigation',
    link: '/website/demandeassurance',
  },
  {
    label: 'Avance',
    icon: 'navigation',
    link: '/website/demandeavance',
  },
  {
    label: 'fiche de paie',
    icon: 'navigation',
    link: '/website/demandefichedep',
  },
];
