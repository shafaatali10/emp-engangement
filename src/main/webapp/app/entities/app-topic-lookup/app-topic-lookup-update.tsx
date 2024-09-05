import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IAppTopicLookup } from 'app/shared/model/app-topic-lookup.model';
import { getEntity, updateEntity, createEntity, reset } from './app-topic-lookup.reducer';

export const AppTopicLookupUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const appTopicLookupEntity = useAppSelector(state => state.appTopicLookup.entity);
  const loading = useAppSelector(state => state.appTopicLookup.loading);
  const updating = useAppSelector(state => state.appTopicLookup.updating);
  const updateSuccess = useAppSelector(state => state.appTopicLookup.updateSuccess);

  const handleClose = () => {
    navigate('/app-topic-lookup');
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

    const entity = {
      ...appTopicLookupEntity,
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
          ...appTopicLookupEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="empEngagementApp.appTopicLookup.home.createOrEditLabel" data-cy="AppTopicLookupCreateUpdateHeading">
            Create or edit a App Topic Lookup
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
                <ValidatedField name="id" required readOnly id="app-topic-lookup-id" label="ID" validate={{ required: true }} />
              ) : null}
              <ValidatedField label="Topic Code" id="app-topic-lookup-topicCode" name="topicCode" data-cy="topicCode" type="text" />
              <ValidatedField label="Topic Name" id="app-topic-lookup-topicName" name="topicName" data-cy="topicName" type="text" />
              <ValidatedField label="Target Group" id="app-topic-lookup-targetGroup" name="targetGroup" data-cy="targetGroup" type="text" />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/app-topic-lookup" replace color="info">
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

export default AppTopicLookupUpdate;
