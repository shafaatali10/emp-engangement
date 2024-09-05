export interface IAppSubjectConfig {
  id?: number;
  subjectCode?: string | null;
  version?: number | null;
  payload?: string | null;
}

export const defaultValue: Readonly<IAppSubjectConfig> = {};
