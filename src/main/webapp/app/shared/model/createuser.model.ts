import { status } from 'app/shared/model/enumerations/status.model';
import { role } from 'app/shared/model/enumerations/role.model';

export interface ICreateuser {
  id?: string;
  rollNo?: string;
  userName?: string;
  password?: string;
  department?: string;
  designation?: string;
  email?: string;
  userImageContentType?: string | null;
  userImage?: string | null;
  roleStatus?: keyof typeof status;
  role?: keyof typeof role;
}

export const defaultValue: Readonly<ICreateuser> = {};
