import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import AppLookup from './app-lookup';
import AppLookupDetail from './app-lookup-detail';
import AppLookupUpdate from './app-lookup-update';
import AppLookupDeleteDialog from './app-lookup-delete-dialog';

const AppLookupRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<AppLookup />} />
    <Route path="new" element={<AppLookupUpdate />} />
    <Route path=":id">
      <Route index element={<AppLookupDetail />} />
      <Route path="edit" element={<AppLookupUpdate />} />
      <Route path="delete" element={<AppLookupDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default AppLookupRoutes;
