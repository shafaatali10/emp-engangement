export interface IAppSubjectType {
  id?: number;
  subjectCode?: string | null;
  description?: string | null;
}

export const defaultValue: Readonly<IAppSubjectType> = {};
