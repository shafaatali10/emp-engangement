import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import AppUserGroup from './app-user-group';
import AppUserGroupDetail from './app-user-group-detail';
import AppUserGroupUpdate from './app-user-group-update';
import AppUserGroupDeleteDialog from './app-user-group-delete-dialog';

const AppUserGroupRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<AppUserGroup />} />
    <Route path="new" element={<AppUserGroupUpdate />} />
    <Route path=":id">
      <Route index element={<AppUserGroupDetail />} />
      <Route path="edit" element={<AppUserGroupUpdate />} />
      <Route path="delete" element={<AppUserGroupDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default AppUserGroupRoutes;
