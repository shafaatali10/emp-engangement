import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './app-user.reducer';

export const AppUserDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const appUserEntity = useAppSelector(state => state.appUser.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="appUserDetailsHeading">App User</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{appUserEntity.id}</dd>
          <dt>
            <span id="firstName">First Name</span>
          </dt>
          <dd>{appUserEntity.firstName}</dd>
          <dt>
            <span id="lastName">Last Name</span>
          </dt>
          <dd>{appUserEntity.lastName}</dd>
          <dt>
            <span id="login">Login</span>
          </dt>
          <dd>{appUserEntity.login}</dd>
          <dt>
            <span id="password">Password</span>
          </dt>
          <dd>{appUserEntity.password}</dd>
          <dt>
            <span id="role">Role</span>
          </dt>
          <dd>{appUserEntity.role}</dd>
          <dt>
            <span id="email">Email</span>
          </dt>
          <dd>{appUserEntity.email}</dd>
        </dl>
        <Button tag={Link} to="/app-user" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/app-user/${appUserEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default AppUserDetail;
