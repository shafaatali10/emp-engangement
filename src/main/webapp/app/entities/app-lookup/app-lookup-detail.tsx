import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './app-lookup.reducer';

export const AppLookupDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const appLookupEntity = useAppSelector(state => state.appLookup.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="appLookupDetailsHeading">App Lookup</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{appLookupEntity.id}</dd>
          <dt>
            <span id="lookupCode">Lookup Code</span>
          </dt>
          <dd>{appLookupEntity.lookupCode}</dd>
          <dt>
            <span id="displayValue">Display Value</span>
          </dt>
          <dd>{appLookupEntity.displayValue}</dd>
          <dt>
            <span id="sequence">Sequence</span>
          </dt>
          <dd>{appLookupEntity.sequence}</dd>
          <dt>
            <span id="category">Category</span>
          </dt>
          <dd>{appLookupEntity.category}</dd>
          <dt>
            <span id="dependentCode">Dependent Code</span>
          </dt>
          <dd>{appLookupEntity.dependentCode}</dd>
        </dl>
        <Button tag={Link} to="/app-lookup" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/app-lookup/${appLookupEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default AppLookupDetail;
