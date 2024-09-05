import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IAppTopicType } from 'app/shared/model/app-topic-type.model';
import { getEntity, updateEntity, createEntity, reset } from './app-topic-type.reducer';

export const AppTopicTypeUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const appTopicTypeEntity = useAppSelector(state => state.appTopicType.entity);
  const loading = useAppSelector(state => state.appTopicType.loading);
  const updating = useAppSelector(state => state.appTopicType.updating);
  const updateSuccess = useAppSelector(state => state.appTopicType.updateSuccess);

  const handleClose = () => {
    navigate('/app-topic-type');
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
      ...appTopicTypeEntity,
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
          ...appTopicTypeEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="empEngagementApp.appTopicType.home.createOrEditLabel" data-cy="AppTopicTypeCreateUpdateHeading">
            Create or edit a App Topic Type
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
                <ValidatedField name="id" required readOnly id="app-topic-type-id" label="ID" validate={{ required: true }} />
              ) : null}
              <ValidatedField label="Topic Code" id="app-topic-type-topicCode" name="topicCode" data-cy="topicCode" type="text" />
              <ValidatedField label="Topic Name" id="app-topic-type-topicName" name="topicName" data-cy="topicName" type="text" />
              <ValidatedField label="Target Group" id="app-topic-type-targetGroup" name="targetGroup" data-cy="targetGroup" type="text" />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/app-topic-type" replace color="info">
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

export default AppTopicTypeUpdate;
