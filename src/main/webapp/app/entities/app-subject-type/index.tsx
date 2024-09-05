import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import AppSubjectType from './app-subject-type';
import AppSubjectTypeDetail from './app-subject-type-detail';
import AppSubjectTypeUpdate from './app-subject-type-update';
import AppSubjectTypeDeleteDialog from './app-subject-type-delete-dialog';

const AppSubjectTypeRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<AppSubjectType />} />
    <Route path="new" element={<AppSubjectTypeUpdate />} />
    <Route path=":id">
      <Route index element={<AppSubjectTypeDetail />} />
      <Route path="edit" element={<AppSubjectTypeUpdate />} />
      <Route path="delete" element={<AppSubjectTypeDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default AppSubjectTypeRoutes;
