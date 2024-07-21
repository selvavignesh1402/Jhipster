import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, openFile, byteSize } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './createuser.reducer';

export const CreateuserDetail = () => {
  const dispatch = useAppDispatch();
  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, [dispatch, id]);

  const createuserEntity = useAppSelector(state => state.createuser.entity);

  return (
    <Row>
      <Col md="8">
        <h2 data-cy="createuserDetailsHeading">
          <Translate contentKey="companyMongoApp.createuser.detail.title">Createuser</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{createuserEntity.id}</dd>
          <dt>
            <span id="rollNo">
              <Translate contentKey="companyMongoApp.createuser.rollNo">Roll No</Translate>
            </span>
          </dt>
          <dd>{createuserEntity.rollNo}</dd>
          <dt>
            <span id="userName">
              <Translate contentKey="companyMongoApp.createuser.userName">User Name</Translate>
            </span>
          </dt>
          <dd>{createuserEntity.userName}</dd>
          <dt>
            <span id="password">
              <Translate contentKey="companyMongoApp.createuser.password">Password</Translate>
            </span>
          </dt>
          <dd>{createuserEntity.password}</dd>
          <dt>
            <span id="department">
              <Translate contentKey="companyMongoApp.createuser.department">Department</Translate>
            </span>
          </dt>
          <dd>{createuserEntity.department ? createuserEntity.department.name : 'N/A'}</dd>
          <dt>
            <span id="designation">
              <Translate contentKey="companyMongoApp.createuser.designation">Designation</Translate>
            </span>
          </dt>
          <dd>{createuserEntity.designation}</dd>
          <dt>
            <span id="email">
              <Translate contentKey="companyMongoApp.createuser.email">Email</Translate>
            </span>
          </dt>
          <dd>{createuserEntity.email}</dd>
          <dt>
            <span id="userImage">
              <Translate contentKey="companyMongoApp.createuser.userImage">User Image</Translate>
            </span>
          </dt>
          <dd>
            {createuserEntity.userImage ? (
              <div>
                {createuserEntity.userImageContentType ? (
                  <a onClick={openFile(createuserEntity.userImageContentType, createuserEntity.userImage)}>
                    <img
                      src={`data:${createuserEntity.userImageContentType};base64,${createuserEntity.userImage}`}
                      style={{ maxHeight: '30px' }}
                      alt="User"
                    />
                  </a>
                ) : null}
                <span>
                  {createuserEntity.userImageContentType}, {byteSize(createuserEntity.userImage)}
                </span>
              </div>
            ) : null}
          </dd>
          <dt>
            <span id="roleStatus">
              <Translate contentKey="companyMongoApp.createuser.roleStatus">Role Status</Translate>
            </span>
          </dt>
          <dd>{createuserEntity.roleStatus}</dd>
          <dt>
            <span id="role">
              <Translate contentKey="companyMongoApp.createuser.role">Role</Translate>
            </span>
          </dt>
          <dd>{createuserEntity.role}</dd>
        </dl>
        <Button tag={Link} to="/createuser" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/createuser/${createuserEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default CreateuserDetail;
