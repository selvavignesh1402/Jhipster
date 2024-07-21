import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Createuser from './createuser';
import CreateuserDetail from './createuser-detail';
import CreateuserUpdate from './createuser-update';
import CreateuserDeleteDialog from './createuser-delete-dialog';

const CreateuserRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Createuser />} />
    <Route path="new" element={<CreateuserUpdate />} />
    <Route path=":id">
      <Route index element={<CreateuserDetail />} />
      <Route path="edit" element={<CreateuserUpdate />} />
      <Route path="delete" element={<CreateuserDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default CreateuserRoutes;
