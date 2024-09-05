import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import AppSubject from './app-subject';
import AppSubjectDetail from './app-subject-detail';
import AppSubjectUpdate from './app-subject-update';
import AppSubjectDeleteDialog from './app-subject-delete-dialog';

const AppSubjectRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<AppSubject />} />
    <Route path="new" element={<AppSubjectUpdate />} />
    <Route path=":id">
      <Route index element={<AppSubjectDetail />} />
      <Route path="edit" element={<AppSubjectUpdate />} />
      <Route path="delete" element={<AppSubjectDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default AppSubjectRoutes;
