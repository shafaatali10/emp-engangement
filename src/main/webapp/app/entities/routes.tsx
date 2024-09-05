import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import AppUser from './app-user';
import AppLookup from './app-lookup';
import AppTopicLookup from './app-topic-lookup';
import AppTopicType from './app-topic-type';
import AppSubjectType from './app-subject-type';
import AppSubject from './app-subject';
import AppSubjectConfig from './app-subject-config';
import AppUserGroup from './app-user-group';
/* jhipster-needle-add-route-import - JHipster will add routes here */

export default () => {
  return (
    <div>
      <ErrorBoundaryRoutes>
        {/* prettier-ignore */}
        <Route path="app-user/*" element={<AppUser />} />
        <Route path="app-lookup/*" element={<AppLookup />} />
        <Route path="app-topic-lookup/*" element={<AppTopicLookup />} />
        <Route path="app-topic-type/*" element={<AppTopicType />} />
        <Route path="app-subject-type/*" element={<AppSubjectType />} />
        <Route path="app-subject/*" element={<AppSubject />} />
        <Route path="app-subject-config/*" element={<AppSubjectConfig />} />
        <Route path="app-user-group/*" element={<AppUserGroup />} />
        {/* jhipster-needle-add-route-path - JHipster will add routes here */}
      </ErrorBoundaryRoutes>
    </div>
  );
};
