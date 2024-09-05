import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import AppTopicLookup from './app-topic-lookup';
import AppTopicLookupDetail from './app-topic-lookup-detail';
import AppTopicLookupUpdate from './app-topic-lookup-update';
import AppTopicLookupDeleteDialog from './app-topic-lookup-delete-dialog';

const AppTopicLookupRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<AppTopicLookup />} />
    <Route path="new" element={<AppTopicLookupUpdate />} />
    <Route path=":id">
      <Route index element={<AppTopicLookupDetail />} />
      <Route path="edit" element={<AppTopicLookupUpdate />} />
      <Route path="delete" element={<AppTopicLookupDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default AppTopicLookupRoutes;
