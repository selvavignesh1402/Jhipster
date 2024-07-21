import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, TextFormat, getPaginationState, JhiPagination, JhiItemCount } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faSort, faSortUp, faSortDown } from '@fortawesome/free-solid-svg-icons';
import { APP_DATE_FORMAT } from 'app/config/constants';
import { ASC, DESC, ITEMS_PER_PAGE, SORT } from 'app/shared/util/pagination.constants';
import { overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities } from './department.reducer';

export const Department = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();

  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getPaginationState(pageLocation, ITEMS_PER_PAGE, 'id'), pageLocation.search),
  );

  const departmentList = useAppSelector(state => state.department.entities);
  const loading = useAppSelector(state => state.department.loading);
  const totalItems = useAppSelector(state => state.department.totalItems);
  const isAuthenticated = useAppSelector(state => state.authentication.isAuthenticated);
  const account = useAppSelector(state => state.authentication.account);

  const getAllEntities = () => {
    dispatch(
      getEntities({
        page: paginationState.activePage - 1,
        size: paginationState.itemsPerPage,
        sort: `${paginationState.sort},${paginationState.order}`,
      }),
    );
  };

  const sortEntities = () => {
    getAllEntities();
    const endURL = `?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`;
    if (pageLocation.search !== endURL) {
      navigate(`${pageLocation.pathname}${endURL}`);
    }
  };

  useEffect(() => {
    sortEntities();
  }, [paginationState.activePage, paginationState.order, paginationState.sort]);

  useEffect(() => {
    const params = new URLSearchParams(pageLocation.search);
    const page = params.get('page');
    const sort = params.get(SORT);
    if (page && sort) {
      const sortSplit = sort.split(',');
      setPaginationState({
        ...paginationState,
        activePage: +page,
        sort: sortSplit[0],
        order: sortSplit[1],
      });
    }
  }, [pageLocation.search]);

  const sort = p => () => {
    setPaginationState({
      ...paginationState,
      order: paginationState.order === ASC ? DESC : ASC,
      sort: p,
    });
  };

  const handlePagination = currentPage =>
    setPaginationState({
      ...paginationState,
      activePage: currentPage,
    });

  const handleSyncList = () => {
    sortEntities();
  };

  const getSortIconByFieldName = (fieldName: string) => {
    const sortFieldName = paginationState.sort;
    const order = paginationState.order;
    if (sortFieldName !== fieldName) {
      return faSort;
    } else {
      return order === ASC ? faSortUp : faSortDown;
    }
  };

  return (
    <div>
      <h2 id="department-heading" data-cy="DepartmentHeading">
        <Translate contentKey="companyMongoApp.department.home.title">Departments</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="companyMongoApp.department.home.refreshListLabel">Refresh List</Translate>
          </Button>
          {isAuthenticated && account.authorities.includes('ROLE_ADMIN') && (
            <Link to="/department/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
              <FontAwesomeIcon icon="plus" />
              &nbsp;
              <Translate contentKey="companyMongoApp.department.home.createLabel">Create new Department</Translate>
            </Link>
          )}
        </div>
      </h2>
      <div className="table-responsive">
        {departmentList && departmentList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  <Translate contentKey="companyMongoApp.department.id">ID</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                </th>
                <th className="hand" onClick={sort('dept_name')}>
                  <Translate contentKey="companyMongoApp.department.dept_name">Dept Name</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('dept_name')} />
                </th>
                <th className="hand" onClick={sort('dept_sname')}>
                  <Translate contentKey="companyMongoApp.department.dept_sname">Dept Sname</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('dept_sname')} />
                </th>
                <th className="hand" onClick={sort('dept_status')}>
                  <Translate contentKey="companyMongoApp.department.dept_status">Dept Status</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('dept_status')} />
                </th>
                <th className="hand" onClick={sort('date')}>
                  <Translate contentKey="companyMongoApp.department.date">Date</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('date')} />
                </th>
                <th>
                  <Translate contentKey="companyMongoApp.department.createuser">Createuser</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {departmentList.map((department, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/department/${department.id}`} color="link" size="sm">
                      {department.id}
                    </Button>
                  </td>
                  <td>{department.dept_name}</td>
                  <td>{department.dept_sname}</td>
                  <td>
                    <Translate contentKey={`companyMongoApp.dept_st.${department.dept_status}`} />
                  </td>
                  <td>{department.date ? <TextFormat type="date" value={department.date} format={APP_DATE_FORMAT} /> : null}</td>
                  <td>
                    {department.createuser ? <Link to={`/createuser/${department.createuser.id}`}>{department.createuser.id}</Link> : ''}
                  </td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/department/${department.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      {isAuthenticated && account.authorities.includes('ROLE_ADMIN') && (
                        <>
                          <Button
                            tag={Link}
                            to={`/department/${department.id}/edit?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
                            color="primary"
                            size="sm"
                            data-cy="entityEditButton"
                          >
                            <FontAwesomeIcon icon="pencil-alt" />{' '}
                            <span className="d-none d-md-inline">
                              <Translate contentKey="entity.action.edit">Edit</Translate>
                            </span>
                          </Button>
                          <Button
                            onClick={() =>
                              (window.location.href = `/department/${department.id}/delete?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`)
                            }
                            color="danger"
                            size="sm"
                            data-cy="entityDeleteButton"
                          >
                            <FontAwesomeIcon icon="trash" />{' '}
                            <span className="d-none d-md-inline">
                              <Translate contentKey="entity.action.delete">Delete</Translate>
                            </span>
                          </Button>
                        </>
                      )}
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && (
            <div className="alert alert-warning">
              <Translate contentKey="companyMongoApp.department.home.notFound">No Departments found</Translate>
            </div>
          )
        )}
      </div>
      {totalItems ? (
        <div className={departmentList && departmentList.length > 0 ? '' : 'd-none'}>
          <div className="justify-content-center d-flex">
            <JhiItemCount page={paginationState.activePage} total={totalItems} itemsPerPage={paginationState.itemsPerPage} i18nEnabled />
          </div>
          <div className="justify-content-center d-flex">
            <JhiPagination
              activePage={paginationState.activePage}
              onSelect={handlePagination}
              maxButtons={5}
              itemsPerPage={paginationState.itemsPerPage}
              totalItems={totalItems}
            />
          </div>
        </div>
      ) : (
        ''
      )}
    </div>
  );
};

export default Department;
