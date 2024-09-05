import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IAppLookup } from 'app/shared/model/app-lookup.model';
import { getEntity, updateEntity, createEntity, reset } from './app-lookup.reducer';

export const AppLookupUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const appLookupEntity = useAppSelector(state => state.appLookup.entity);
  const loading = useAppSelector(state => state.appLookup.loading);
  const updating = useAppSelector(state => state.appLookup.updating);
  const updateSuccess = useAppSelector(state => state.appLookup.updateSuccess);

  const handleClose = () => {
    navigate('/app-lookup');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  // eslint-disable-next-line complexity
  const saveEntity = values => {
    if (values.id !== undefined && typeof values.id !== 'number') {
      values.id = Number(values.id);
    }
    if (values.sequence !== undefined && typeof values.sequence !== 'number') {
      values.sequence = Number(values.sequence);
    }

    const entity = {
      ...appLookupEntity,
      ...values,
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
          ...appLookupEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="empEngagementApp.appLookup.home.createOrEditLabel" data-cy="AppLookupCreateUpdateHeading">
            Create or edit a App Lookup
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="app-lookup-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField label="Lookup Code" id="app-lookup-lookupCode" name="lookupCode" data-cy="lookupCode" type="text" />
              <ValidatedField label="Display Value" id="app-lookup-displayValue" name="displayValue" data-cy="displayValue" type="text" />
              <ValidatedField label="Sequence" id="app-lookup-sequence" name="sequence" data-cy="sequence" type="text" />
              <ValidatedField label="Category" id="app-lookup-category" name="category" data-cy="category" type="text" />
              <ValidatedField
                label="Dependent Code"
                id="app-lookup-dependentCode"
                name="dependentCode"
                data-cy="dependentCode"
                type="text"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/app-lookup" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">Back</span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp; Save
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default AppLookupUpdate;
