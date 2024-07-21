// import React, { useState, useEffect } from 'react';
// import { Link, useNavigate, useParams } from 'react-router-dom';
// import { Button, Row, Col, FormText } from 'reactstrap';
// import { isNumber, Translate, translate, ValidatedField, ValidatedForm, ValidatedBlobField } from 'react-jhipster';
// import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

// import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
// import { mapIdList } from 'app/shared/util/entity-utils';
// import { useAppDispatch, useAppSelector } from 'app/config/store';

// import { ICreateuser } from 'app/shared/model/createuser.model';
// import { status } from 'app/shared/model/enumerations/status.model';
// import { role } from 'app/shared/model/enumerations/role.model';
// import { getEntity, updateEntity, createEntity, reset } from './createuser.reducer';

// export const CreateuserUpdate = () => {
//   const dispatch = useAppDispatch();

//   const navigate = useNavigate();

//   const { id } = useParams<'id'>();
//   const isNew = id === undefined;

//   const createuserEntity = useAppSelector(state => state.createuser.entity);
//   const loading = useAppSelector(state => state.createuser.loading);
//   const updating = useAppSelector(state => state.createuser.updating);
//   const updateSuccess = useAppSelector(state => state.createuser.updateSuccess);
//   const statusValues = Object.keys(status);
//   const roleValues = Object.keys(role);

//   const handleClose = () => {
//     navigate('/createuser' + location.search);
//   };

//   useEffect(() => {
//     if (isNew) {
//       dispatch(reset());
//     } else {
//       dispatch(getEntity(id));
//     }
//   }, []);

//   useEffect(() => {
//     if (updateSuccess) {
//       handleClose();
//     }
//   }, [updateSuccess]);

//   // eslint-disable-next-line complexity
//   const saveEntity = values => {
//     const entity = {
//       ...createuserEntity,
//       ...values,
//     };

//     if (isNew) {
//       dispatch(createEntity(entity));
//     } else {
//       dispatch(updateEntity(entity));
//     }
//   };

//   const defaultValues = () =>
//     isNew
//       ? {}
//       : {
//           roleStatus: 'S1',
//           role: 'S1',
//           ...createuserEntity,
//         };

//   return (
//     <div>
//       <Row className="justify-content-center">
//         <Col md="8">
//           <h2 id="companyMongoApp.createuser.home.createOrEditLabel" data-cy="CreateuserCreateUpdateHeading">
//             <Translate contentKey="companyMongoApp.createuser.home.createOrEditLabel">Create or edit a Createuser</Translate>
//           </h2>
//         </Col>
//       </Row>
//       <Row className="justify-content-center">
//         <Col md="8">
//           {loading ? (
//             <p>Loading...</p>
//           ) : (
//             <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
//               {!isNew ? (
//                 <ValidatedField
//                   name="id"
//                   required
//                   readOnly
//                   id="createuser-id"
//                   label={translate('global.field.id')}
//                   validate={{ required: true }}
//                 />
//               ) : null}
//               <ValidatedField
//                 label={translate('companyMongoApp.createuser.rollNo')}
//                 id="createuser-rollNo"
//                 name="rollNo"
//                 data-cy="rollNo"
//                 type="text"
//                 validate={{
//                   required: { value: true, message: translate('entity.validation.required') },
//                   pattern: { value: /^[a-zA-Z0-9]*$/, message: translate('entity.validation.pattern', { pattern: '^[a-zA-Z0-9]*$' }) },
//                 }}
//               />
//               <ValidatedField
//                 label={translate('companyMongoApp.createuser.userName')}
//                 id="createuser-userName"
//                 name="userName"
//                 data-cy="userName"
//                 type="text"
//                 validate={{
//                   required: { value: true, message: translate('entity.validation.required') },
//                 }}
//               />
//               <ValidatedField
//                 label={translate('companyMongoApp.createuser.password')}
//                 id="createuser-password"
//                 name="password"
//                 data-cy="password"
//                 type="text"
//                 validate={{
//                   required: { value: true, message: translate('entity.validation.required') },
//                 }}
//               />
//               <ValidatedField
//                 label={translate('companyMongoApp.createuser.department')}
//                 id="createuser-department"
//                 name="department"
//                 data-cy="department"
//                 type="text"
//                 validate={{
//                   required: { value: true, message: translate('entity.validation.required') },
//                 }}
//               />
//               <ValidatedField
//                 label={translate('companyMongoApp.createuser.designation')}
//                 id="createuser-designation"
//                 name="designation"
//                 data-cy="designation"
//                 type="text"
//                 validate={{
//                   required: { value: true, message: translate('entity.validation.required') },
//                 }}
//               />
//               <ValidatedField
//                 label={translate('companyMongoApp.createuser.email')}
//                 id="createuser-email"
//                 name="email"
//                 data-cy="email"
//                 type="text"
//                 validate={{
//                   required: { value: true, message: translate('entity.validation.required') },
//                 }}
//               />
//               <ValidatedBlobField
//                 label={translate('companyMongoApp.createuser.userImage')}
//                 id="createuser-userImage"
//                 name="userImage"
//                 data-cy="userImage"
//                 isImage
//                 accept="image/*"
//               />
//               <ValidatedField
//                 label={translate('companyMongoApp.createuser.roleStatus')}
//                 id="createuser-roleStatus"
//                 name="roleStatus"
//                 data-cy="roleStatus"
//                 type="select"
//               >
//                 {statusValues.map(status => (
//                   <option value={status} key={status}>
//                     {translate('companyMongoApp.status.' + status)}
//                   </option>
//                 ))}
//               </ValidatedField>
//               <ValidatedField
//                 label={translate('companyMongoApp.createuser.role')}
//                 id="createuser-role"
//                 name="role"
//                 data-cy="role"
//                 type="select"
//               >
//                 {roleValues.map(role => (
//                   <option value={role} key={role}>
//                     {translate('companyMongoApp.role.' + role)}
//                   </option>
//                 ))}
//               </ValidatedField>
//               <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/createuser" replace color="info">
//                 <FontAwesomeIcon icon="arrow-left" />
//                 &nbsp;
//                 <span className="d-none d-md-inline">
//                   <Translate contentKey="entity.action.back">Back</Translate>
//                 </span>
//               </Button>
//               &nbsp;
//               <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
//                 <FontAwesomeIcon icon="save" />
//                 &nbsp;
//                 <Translate contentKey="entity.action.save">Save</Translate>
//               </Button>
//             </ValidatedForm>
//           )}
//         </Col>
//       </Row>
//     </div>
//   );
// };

// export default CreateuserUpdate;

import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm, ValidatedBlobField } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ICreateuser } from 'app/shared/model/createuser.model';
import { status } from 'app/shared/model/enumerations/status.model';
import { role } from 'app/shared/model/enumerations/role.model';
import { getEntity, updateEntity, createEntity, reset } from './createuser.reducer';
import { getEntities as getDepartments } from 'app/entities/department/department.reducer';
import { IDepartment } from 'app/shared/model/department.model';

export const CreateuserUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const createuserEntity = useAppSelector(state => state.createuser.entity);
  const loading = useAppSelector(state => state.createuser.loading);
  const updating = useAppSelector(state => state.createuser.updating);
  const updateSuccess = useAppSelector(state => state.createuser.updateSuccess);
  const statusValues = Object.keys(status);
  const roleValues = Object.keys(role);

  const departments = useAppSelector(state => state.department.entities);

  const handleClose = () => {
    navigate('/createuser' + location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getDepartments({ page: 0, size: 100, sort: 'id,asc' }));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  // eslint-disable-next-line complexity
  const saveEntity = values => {
    const entity = {
      ...createuserEntity,
      ...values,
      department: departments.find(it => it.id.toString() === values.department.toString()),
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {}
      : {
          roleStatus: 'S1',
          role: 'S1',
          ...createuserEntity,
          department: createuserEntity?.department?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="companyMongoApp.createuser.home.createOrEditLabel" data-cy="CreateuserCreateUpdateHeading">
            <Translate contentKey="companyMongoApp.createuser.home.createOrEditLabel">Create or edit a Createuser</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? (
                <ValidatedField
                  name="id"
                  required
                  readOnly
                  id="createuser-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('companyMongoApp.createuser.rollNo')}
                id="createuser-rollNo"
                name="rollNo"
                data-cy="rollNo"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  pattern: { value: /^[a-zA-Z0-9]*$/, message: translate('entity.validation.pattern', { pattern: '^[a-zA-Z0-9]*$' }) },
                }}
              />
              <ValidatedField
                label={translate('companyMongoApp.createuser.userName')}
                id="createuser-userName"
                name="userName"
                data-cy="userName"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('companyMongoApp.createuser.password')}
                id="createuser-password"
                name="password"
                data-cy="password"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label="Department"
                id="createuser-department"
                name="department"
                data-cy="department"
                type="select"
                validate={{
                  required: { value: true, message: 'This field is required' },
                }}
                style={{
                  color: 'black',
                  backgroundColor: 'white',
                }}
              >
                <option value="" key="0">
                  Select a department
                </option>
                {departments.map(department => (
                  <option value={department.id} key={department.id}>
                    {department.dept_name}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                label={translate('companyMongoApp.createuser.designation')}
                id="createuser-designation"
                name="designation"
                data-cy="designation"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('companyMongoApp.createuser.email')}
                id="createuser-email"
                name="email"
                data-cy="email"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedBlobField
                label={translate('companyMongoApp.createuser.userImage')}
                id="createuser-userImage"
                name="userImage"
                data-cy="userImage"
                isImage
                accept="image/*"
              />
              <ValidatedField
                label={translate('companyMongoApp.createuser.roleStatus')}
                id="createuser-roleStatus"
                name="roleStatus"
                data-cy="roleStatus"
                type="select"
              >
                {statusValues.map(status => (
                  <option value={status} key={status}>
                    {translate('companyMongoApp.status.' + status)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                label={translate('companyMongoApp.createuser.role')}
                id="createuser-role"
                name="role"
                data-cy="role"
                type="select"
              >
                {roleValues.map(role => (
                  <option value={role} key={role}>
                    {translate('companyMongoApp.role.' + role)}
                  </option>
                ))}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/createuser" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp;
                <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default CreateuserUpdate;
