import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import AppTopicType from './app-topic-type';
import AppTopicTypeDetail from './app-topic-type-detail';
import AppTopicTypeUpdate from './app-topic-type-update';
import AppTopicTypeDeleteDialog from './app-topic-type-delete-dialog';

const AppTopicTypeRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<AppTopicType />} />
    <Route path="new" element={<AppTopicTypeUpdate />} />
    <Route path=":id">
      <Route index element={<AppTopicTypeDetail />} />
      <Route path="edit" element={<AppTopicTypeUpdate />} />
      <Route path="delete" element={<AppTopicTypeDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default AppTopicTypeRoutes;
