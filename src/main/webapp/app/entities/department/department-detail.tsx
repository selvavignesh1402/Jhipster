import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './department.reducer';

export const DepartmentDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const departmentEntity = useAppSelector(state => state.department.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="departmentDetailsHeading">
          <Translate contentKey="companyMongoApp.department.detail.title">Department</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{departmentEntity.id}</dd>
          <dt>
            <span id="dept_name">
              <Translate contentKey="companyMongoApp.department.dept_name">Dept Name</Translate>
            </span>
          </dt>
          <dd>{departmentEntity.dept_name}</dd>
          <dt>
            <span id="dept_sname">
              <Translate contentKey="companyMongoApp.department.dept_sname">Dept Sname</Translate>
            </span>
          </dt>
          <dd>{departmentEntity.dept_sname}</dd>
          <dt>
            <span id="dept_status">
              <Translate contentKey="companyMongoApp.department.dept_status">Dept Status</Translate>
            </span>
          </dt>
          <dd>{departmentEntity.dept_status}</dd>
          <dt>
            <span id="date">
              <Translate contentKey="companyMongoApp.department.date">Date</Translate>
            </span>
          </dt>
          <dd>{departmentEntity.date ? <TextFormat value={departmentEntity.date} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <Translate contentKey="companyMongoApp.department.createuser">Createuser</Translate>
          </dt>
          <dd>{departmentEntity.createuser ? departmentEntity.createuser.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/department" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/department/${departmentEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default DepartmentDetail;
