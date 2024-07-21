import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ICreateuser } from 'app/shared/model/createuser.model';
import { getEntities as getCreateusers } from 'app/entities/createuser/createuser.reducer';
import { IDepartment } from 'app/shared/model/department.model';
import { dept_st } from 'app/shared/model/enumerations/dept-st.model';
import { getEntity, updateEntity, createEntity, reset } from './department.reducer';

export const DepartmentUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const createusers = useAppSelector(state => state.createuser.entities);
  const departmentEntity = useAppSelector(state => state.department.entity);
  const loading = useAppSelector(state => state.department.loading);
  const updating = useAppSelector(state => state.department.updating);
  const updateSuccess = useAppSelector(state => state.department.updateSuccess);
  const dept_stValues = Object.keys(dept_st);

  const handleClose = () => {
    navigate('/department' + location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getCreateusers({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  // eslint-disable-next-line complexity
  const saveEntity = values => {
    values.date = convertDateTimeToServer(values.date);

    const entity = {
      ...departmentEntity,
      ...values,
      createuser: createusers.find(it => it.id.toString() === values.createuser?.toString()),
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {
          date: displayDefaultDateTime(),
        }
      : {
          dept_status: 'ACTIVE',
          ...departmentEntity,
          date: convertDateTimeFromServer(departmentEntity.date),
          createuser: departmentEntity?.createuser?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="companyMongoApp.department.home.createOrEditLabel" data-cy="DepartmentCreateUpdateHeading">
            <Translate contentKey="companyMongoApp.department.home.createOrEditLabel">Create or edit a Department</Translate>
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
                  id="department-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('companyMongoApp.department.dept_name')}
                id="department-dept_name"
                name="dept_name"
                data-cy="dept_name"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  pattern: { value: /^[a-zA-Z0-9]*$/, message: translate('entity.validation.pattern', { pattern: '^[a-zA-Z0-9]*$' }) },
                }}
              />
              <ValidatedField
                label={translate('companyMongoApp.department.dept_sname')}
                id="department-dept_sname"
                name="dept_sname"
                data-cy="dept_sname"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  pattern: { value: /^[a-zA-Z0-9]*$/, message: translate('entity.validation.pattern', { pattern: '^[a-zA-Z0-9]*$' }) },
                }}
              />
              <ValidatedField
                label={translate('companyMongoApp.department.dept_status')}
                id="department-dept_status"
                name="dept_status"
                data-cy="dept_status"
                type="select"
              >
                {dept_stValues.map(dept_st => (
                  <option value={dept_st} key={dept_st}>
                    {translate('companyMongoApp.dept_st.' + dept_st)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                label={translate('companyMongoApp.department.date')}
                id="department-date"
                name="date"
                data-cy="date"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                id="department-createuser"
                name="createuser"
                data-cy="createuser"
                label={translate('companyMongoApp.department.createuser')}
                type="select"
              >
                <option value="" key="0" />
                {createusers
                  ? createusers.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/department" replace color="info">
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

export default DepartmentUpdate;
