import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useLocation } from 'react-router-dom';
import { Button, Table, Badge, Row } from 'reactstrap';
import { Translate, TextFormat, JhiPagination, JhiItemCount, getPaginationState } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faSort, faSortUp, faSortDown } from '@fortawesome/free-solid-svg-icons';
import { useAppDispatch, useAppSelector } from 'app/config/store';
import { getEntities } from './createuser.reducer';
import { ITEMS_PER_PAGE, SORT, ASC, DESC } from 'app/shared/util/pagination.constants';

const Createuser = () => {
  const dispatch = useAppDispatch();
  const location = useLocation();
  const navigate = useNavigate();

  const [paginationState, setPaginationState] = useState(getPaginationState(location, ITEMS_PER_PAGE, 'id'));

  const getAllEntities = () => {
    dispatch(
      getEntities({
        page: paginationState.activePage - 1,
        size: paginationState.itemsPerPage,
        sort: `${paginationState.sort},${paginationState.order}`,
      }),
    );
    const endURL = `?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`;
    if (location.search !== endURL) {
      navigate(`${location.pathname}${endURL}`);
    }
  };

  useEffect(() => {
    getAllEntities();
  }, [paginationState.activePage, paginationState.order, paginationState.sort]);

  useEffect(() => {
    const params = new URLSearchParams(location.search);
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
  }, [location.search]);

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
    getAllEntities();
  };

  const account = useAppSelector(state => state.authentication.account);
  const createuserList = useAppSelector(state => state.createuser.entities);
  const loading = useAppSelector(state => state.createuser.loading);
  const totalItems = useAppSelector(state => state.createuser.totalItems);
  const isAuthenticated = useAppSelector(state => state.authentication.isAuthenticated);

  const getSortIconByFieldName = fieldName => {
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
      <h2 id="createuser-heading" data-cy="CreateuserHeading">
        <Translate contentKey="companyMongoApp.createuser.home.title">Createusers</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="companyMongoApp.createuser.home.refreshListLabel">Refresh List</Translate>
          </Button>
          {isAuthenticated && account.authorities.includes('ROLE_ADMIN') && (
            <Link to="/createuser/new" className="btn btn-primary jh-create-entity">
              <FontAwesomeIcon icon="plus" />
              <Translate contentKey="companyMongoApp.createuser.home.createLabel">Create a new Createuser</Translate>
            </Link>
          )}
        </div>
      </h2>
      <div className="table-responsive">
        {createuserList && createuserList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  <Translate contentKey="global.field.id">ID</Translate>
                  <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                </th>
                <th className="hand" onClick={sort('rollNo')}>
                  <Translate contentKey="companyMongoApp.createuser.rollNo">Roll No</Translate>
                  <FontAwesomeIcon icon={getSortIconByFieldName('rollNo')} />
                </th>
                <th className="hand" onClick={sort('userName')}>
                  <Translate contentKey="companyMongoApp.createuser.userName">User Name</Translate>
                  <FontAwesomeIcon icon={getSortIconByFieldName('userName')} />
                </th>
                <th className="hand" onClick={sort('password')}>
                  <Translate contentKey="companyMongoApp.createuser.password">Password</Translate>
                  <FontAwesomeIcon icon={getSortIconByFieldName('password')} />
                </th>
                <th className="hand" onClick={sort('department.dept_name')}>
                  <Translate contentKey="companyMongoApp.createuser.department">Department</Translate>
                  <FontAwesomeIcon icon={getSortIconByFieldName('department')} />
                </th>
                <th className="hand" onClick={sort('designation')}>
                  <Translate contentKey="companyMongoApp.createuser.designation">Designation</Translate>
                  <FontAwesomeIcon icon={getSortIconByFieldName('designation')} />
                </th>
                <th className="hand" onClick={sort('email')}>
                  <Translate contentKey="companyMongoApp.createuser.email">Email</Translate>
                  <FontAwesomeIcon icon={getSortIconByFieldName('email')} />
                </th>
                <th className="hand" onClick={sort('userImage')}>
                  <Translate contentKey="companyMongoApp.createuser.userImage">User Image</Translate>
                  <FontAwesomeIcon icon={getSortIconByFieldName('userImage')} />
                </th>
                <th className="hand" onClick={sort('roleStatus')}>
                  <Translate contentKey="companyMongoApp.createuser.roleStatus">Role Status</Translate>
                  <FontAwesomeIcon icon={getSortIconByFieldName('roleStatus')} />
                </th>
                <th className="hand" onClick={sort('role')}>
                  <Translate contentKey="companyMongoApp.createuser.role">Role</Translate>
                  <FontAwesomeIcon icon={getSortIconByFieldName('role')} />
                </th>
                {/* <th className="hand" onClick={sort('login')}>
                  <Translate contentKey="companyMongoApp.createuser.login">Login</Translate>
                  <FontAwesomeIcon icon={getSortIconByFieldName('login')} />
                </th>
                <th className="hand" onClick={sort('langKey')}>
                  <Translate contentKey="companyMongoApp.createuser.langKey">Lang Key</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('langKey')} />
                </th>
                <th /> */}
              </tr>
            </thead>
            <tbody>
              {createuserList.map((createuser, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/createuser/${createuser.id}`} color="link" size="sm">
                      {createuser.id}
                    </Button>
                  </td>
                  <td>{createuser.rollNo}</td>
                  <td>{createuser.userName}</td>
                  <td>{createuser.password}</td>
                  <td>{createuser.department ? createuser.department.dept_name : 'N/A'}</td>
                  <td>{createuser.department ? createuser.department.dept_sname : 'N/A'}</td>
                  <td>{createuser.department ? createuser.department.dept_status : 'N/A'}</td>
                  <td>{createuser.designation}</td>
                  <td>{createuser.email}</td>
                  <td>{createuser.userImage}</td>
                  {/* <td>{createuser.roleStatus}</td>
                  <td>{createuser.role}</td> */}
                  {/* <td>{createuser.login}</td>
                  <td>{createuser.langKey}</td> */}
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/createuser/${createuser.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      {isAuthenticated && account.authorities.includes('ROLE_ADMIN') && (
                        <>
                          <Button
                            tag={Link}
                            to={`/createuser/${createuser.id}/edit?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
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
                              (window.location.href = `/createuser/${createuser.id}/delete?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`)
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
              <Translate contentKey="companyMongoApp.createuser.home.notFound">No Createusers found</Translate>
            </div>
          )
        )}
      </div>
      {totalItems ? (
        <div className={createuserList && createuserList.length > 0 ? '' : 'd-none'}>
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

export default Createuser;
