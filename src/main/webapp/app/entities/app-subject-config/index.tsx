import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import AppSubjectConfig from './app-subject-config';
import AppSubjectConfigDetail from './app-subject-config-detail';
import AppSubjectConfigUpdate from './app-subject-config-update';
import AppSubjectConfigDeleteDialog from './app-subject-config-delete-dialog';

const AppSubjectConfigRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<AppSubjectConfig />} />
    <Route path="new" element={<AppSubjectConfigUpdate />} />
    <Route path=":id">
      <Route index element={<AppSubjectConfigDetail />} />
      <Route path="edit" element={<AppSubjectConfigUpdate />} />
      <Route path="delete" element={<AppSubjectConfigDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default AppSubjectConfigRoutes;
